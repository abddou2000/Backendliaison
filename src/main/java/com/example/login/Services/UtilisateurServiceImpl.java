package com.example.login.Services;

import com.example.login.Models.Role;
import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository userRepo;
    private final RoleService roleService;
    private final PasswordEncoder encoder;
    private final EmailService emailService;

    public UtilisateurServiceImpl(UtilisateurRepository userRepo,
                                  RoleService roleService,
                                  PasswordEncoder encoder,
                                  EmailService emailService) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.encoder = encoder;
        this.emailService = emailService;
    }

    @Override
    public Utilisateur getById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'ID : " + id));
    }

    @Override
    public Utilisateur getByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec le nom d'utilisateur : " + username));
    }

    @Override
    public List<Utilisateur> getAll() {
        return userRepo.findAll();
    }

    @Override
    @Transactional
    public Utilisateur create(Utilisateur user, String roleType) {
        Role role = roleService.getByType(roleType);
        user.setRole(role);
        user.setDateCreation(LocalDateTime.now());
        user.setDateModification(LocalDateTime.now());

        String motDePasseEnClair;

        if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            motDePasseEnClair = user.getPassword();
            user.setPasswordHash(encoder.encode(user.getPassword()));
        } else {
            motDePasseEnClair = genererMotDePasseAleatoire();
            user.setPasswordHash(encoder.encode(motDePasseEnClair));
        }

        user.setEtatCompte(Utilisateur.EtatCompte.ACTIF);
        user.setDateExpirationMdp(LocalDateTime.now().plusMonths(6));

        Utilisateur savedUser = userRepo.save(user);

        emailService.envoyerEmailSimple(user.getEmail(), "Votre nom d'utilisateur pour [Nom App]",
                "Bonjour " + user.getPrenom() + ",\n\nVotre nom d'utilisateur est : " + user.getUsername());

        emailService.envoyerEmailSimple(user.getEmail(), "Votre mot de passe pour [Nom App]",
                "Bonjour " + user.getPrenom() + ",\n\nVotre mot de passe est : " + motDePasseEnClair +
                        "\n\nVotre compte est maintenant actif et vous pouvez vous connecter directement.");

        return savedUser;
    }

    @Override
    @Transactional
    public void updatePassword(Long userId, String ancienMdp, String nouveauMdp, String confirmationMdp) {
        if (!nouveauMdp.equals(confirmationMdp)) {
            throw new IllegalArgumentException("Le nouveau mot de passe et sa confirmation ne correspondent pas.");
        }
        validerComplexiteMdp(nouveauMdp);

        Utilisateur user = getById(userId);

        if (!encoder.matches(ancienMdp, user.getPasswordHash())) {
            throw new IllegalArgumentException("L'ancien mot de passe est incorrect.");
        }

        user.setPasswordHash(encoder.encode(nouveauMdp));
        user.setDateExpirationMdp(LocalDateTime.now().plusMonths(3));
        user.setEtatCompte(Utilisateur.EtatCompte.ACTIF);
        user.setDateModification(LocalDateTime.now());
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void demanderReinitialisation(String email) {
        Utilisateur user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Aucun utilisateur trouvé avec cet email."));

        String token = UUID.randomUUID().toString();
        user.setTokenReinitialisation(token);
        user.setDateExpirationToken(LocalDateTime.now().plusHours(1));
        user.setDateModification(LocalDateTime.now());
        userRepo.save(user);

        String lien = "http://localhost:4200/reset-password?token=" + token;
        emailService.envoyerEmailSimple(email, "Réinitialisation de votre mot de passe",
                "Cliquez sur ce lien pour réinitialiser votre mot de passe : " + lien);
    }

    @Override
    @Transactional
    public void reinitialiserMdp(String token, String nouveauMdp, String confirmationMdp) {
        if (!nouveauMdp.equals(confirmationMdp)) {
            throw new IllegalArgumentException("Les mots de passe ne correspondent pas.");
        }
        validerComplexiteMdp(nouveauMdp);

        Utilisateur user = userRepo.findByTokenReinitialisation(token)
                .orElseThrow(() -> new RuntimeException("Token invalide ou expiré."));

        if (user.getDateExpirationToken().isBefore(LocalDateTime.now())) {
            user.setTokenReinitialisation(null);
            user.setDateExpirationToken(null);
            user.setDateModification(LocalDateTime.now());
            userRepo.save(user);
            throw new RuntimeException("Token expiré.");
        }

        user.setPasswordHash(encoder.encode(nouveauMdp));
        user.setDateExpirationMdp(LocalDateTime.now().plusMonths(3));
        user.setEtatCompte(Utilisateur.EtatCompte.ACTIF);
        user.setTokenReinitialisation(null);
        user.setDateExpirationToken(null);
        user.setDateModification(LocalDateTime.now());
        userRepo.save(user);
    }

    @Override
    public List<Utilisateur> getByRole(String roleType) {
        return userRepo.findByRole_Type(roleType);
    }

    @Override
    @Transactional
    public Utilisateur updateUtilisateurDetails(Long id, String nom, String prenom, String email, String username, String matricule, Boolean actif) {
        Utilisateur existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + id));

        if (nom != null && !nom.trim().isEmpty() && !nom.trim().equals(existingUser.getNom())) {
            existingUser.setNom(nom.trim());
        }
        if (prenom != null && !prenom.trim().isEmpty() && !prenom.trim().equals(existingUser.getPrenom())) {
            existingUser.setPrenom(prenom.trim());
        }
        if (email != null && !email.trim().isEmpty() && !email.trim().equals(existingUser.getEmail())) {
            if (userRepo.findByEmail(email.trim()).isPresent() &&
                    !userRepo.findByEmail(email.trim()).get().getId().equals(id)) {
                throw new IllegalArgumentException("L'email '" + email + "' est déjà utilisé par un autre utilisateur.");
            }
            existingUser.setEmail(email.trim());
        }
        if (username != null && !username.trim().isEmpty() && !username.trim().equals(existingUser.getUsername())) {
            if (userRepo.findByUsername(username.trim()).isPresent() &&
                    !userRepo.findByUsername(username.trim()).get().getId().equals(id)) {
                throw new IllegalArgumentException("Le nom d'utilisateur '" + username + "' est déjà pris.");
            }
            existingUser.setUsername(username.trim());
        }
        if (matricule != null && !matricule.trim().isEmpty() && !matricule.trim().equals(existingUser.getMatricule())) {
            if (userRepo.findByMatricule(matricule.trim()).isPresent() &&
                    !userRepo.findByMatricule(matricule.trim()).get().getId().equals(id)) {
                throw new IllegalArgumentException("Le matricule '" + matricule + "' est déjà utilisé par un autre utilisateur.");
            }
            existingUser.setMatricule(matricule.trim());
        } else if (matricule != null && matricule.trim().isEmpty() && existingUser.getMatricule() != null) {
            existingUser.setMatricule(null);
        }

        if (actif != null) {
            if (actif) {
                existingUser.setEtatCompte(Utilisateur.EtatCompte.ACTIF);
            } else {
                existingUser.setEtatCompte(Utilisateur.EtatCompte.BLOQUE);
            }
        }

        existingUser.setDateModification(LocalDateTime.now());
        return userRepo.save(existingUser);
    }

    @Override
    @Transactional
    public void adminResetPassword(Long userId, String nouveauMdp) {
        validerComplexiteMdp(nouveauMdp);

        Utilisateur user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userId));

        user.setPasswordHash(encoder.encode(nouveauMdp));
        user.setDateExpirationMdp(LocalDateTime.now().plusMonths(6));
        user.setEtatCompte(Utilisateur.EtatCompte.ACTIF);
        user.setDateModification(LocalDateTime.now());

        userRepo.save(user);

        emailService.envoyerEmailSimple(
                user.getEmail(),
                "Votre mot de passe a été réinitialisé",
                "Bonjour " + user.getPrenom() + ",\n\n" +
                        "Votre mot de passe a été réinitialisé par un administrateur.\n" +
                        "Votre nouveau mot de passe est : " + nouveauMdp + "\n\n" +
                        "Pour des raisons de sécurité, nous vous recommandons de le changer dès votre prochaine connexion.\n\n" +
                        "Cordialement,\n" +
                        "L'équipe [Nom App]"
        );
    }

    @Override
    @Transactional
    public void deleteUtilisateur(Long userId) {
        Utilisateur user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userId));

        emailService.envoyerEmailSimple(
                user.getEmail(),
                "Suppression de votre compte",
                "Bonjour " + user.getPrenom() + ",\n\n" +
                        "Votre compte a été supprimé par un administrateur.\n\n" +
                        "Si vous pensez qu'il s'agit d'une erreur, veuillez contacter l'administration.\n\n" +
                        "Cordialement,\n" +
                        "L'équipe [Nom App]"
        );

        userRepo.delete(user);
    }

    private String genererMotDePasseAleatoire() {
        return "Temp" + UUID.randomUUID().toString().substring(0, 4).toUpperCase() + "$";
    }

    private void validerComplexiteMdp(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 8 caractères.");
        }
        Pattern hasUppercase = Pattern.compile("[A-Z]");
        Pattern hasLowercase = Pattern.compile("[a-z]");
        Pattern hasDigit = Pattern.compile("[0-9]");
        Pattern hasSpecialChar = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");

        if (!hasUppercase.matcher(password).find() ||
                !hasLowercase.matcher(password).find() ||
                !hasDigit.matcher(password).find() ||
                !hasSpecialChar.matcher(password).find()) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins une majuscule, une minuscule, un chiffre et un caractère spécial.");
        }
    }
}