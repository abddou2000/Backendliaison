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
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable : " + id));
    }

    @Override
    public Utilisateur getByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable : " + username));
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

        String motDePasseTemporaire = genererMotDePasseAleatoire();
        user.setPasswordHash(encoder.encode(motDePasseTemporaire));

        user.setEtatCompte(Utilisateur.EtatCompte.EN_ATTENTE_CHANGEMENT_MDP);
        user.setDateExpirationMdp(LocalDateTime.now().plusDays(3));

        Utilisateur savedUser = userRepo.save(user);

        // Envoyer les emails
        emailService.envoyerEmailSimple(user.getEmail(), "Votre nom d'utilisateur pour [Nom App]", "Bonjour " + user.getPrenom() + ",\n\nVotre nom d'utilisateur est : " + user.getUsername());
        emailService.envoyerEmailSimple(user.getEmail(), "Votre mot de passe initial pour [Nom App]", "Bonjour " + user.getPrenom() + ",\n\nVotre mot de passe temporaire est : " + motDePasseTemporaire + "\nIl est valide pendant 3 jours.");

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
        userRepo.save(user);

        String lien = "http://localhost:4200/reset-password?token=" + token; // Mettez l'URL de votre frontend
        emailService.envoyerEmailSimple(email, "Réinitialisation de votre mot de passe", "Cliquez sur ce lien pour réinitialiser votre mot de passe : " + lien);
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
            userRepo.save(user);
            throw new RuntimeException("Token expiré.");
        }

        user.setPasswordHash(encoder.encode(nouveauMdp));
        user.setDateExpirationMdp(LocalDateTime.now().plusMonths(3));
        user.setEtatCompte(Utilisateur.EtatCompte.ACTIF);
        user.setTokenReinitialisation(null);
        user.setDateExpirationToken(null);
        userRepo.save(user);
    }

    // N'oubliez pas d'ajouter findByTokenReinitialisation dans UtilisateurRepository
    // public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    //    ...
    //    Optional<Utilisateur> findByTokenReinitialisation(String token);
    // }

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

        if (!hasUppercase.matcher(password).find() || !hasLowercase.matcher(password).find() || !hasDigit.matcher(password).find() || !hasSpecialChar.matcher(password).find()) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins une majuscule, une minuscule, un chiffre et un caractère spécial.");
        }
    }
}