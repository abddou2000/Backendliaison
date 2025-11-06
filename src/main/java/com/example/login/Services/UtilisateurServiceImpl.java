package com.example.login.Services;

import com.example.login.Models.ActivityLog;
import com.example.login.Models.Role;
import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.UtilisateurRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private ActivityLogService activityLogService; // ✅ Injection du service de log

    @Autowired(required = false)
    private HttpServletRequest request; // ✅ Pour récupérer l'IP et le User-Agent

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

        // ✅ Logger l'activité de création
        logActivity(
                "Création d'un utilisateur",
                ActivityLog.ActivityType.CREATION,
                ActivityLog.ActivityStatus.SUCCESS,
                user.getUsername() + " (" + user.getEmail() + ")"
        );

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

        // ✅ Logger l'activité de modification du mot de passe
        logActivity(
                "Modification du mot de passe",
                ActivityLog.ActivityType.MODIFICATION,
                ActivityLog.ActivityStatus.SUCCESS,
                user.getUsername()
        );
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

        // ✅ Logger l'activité de demande de réinitialisation
        logActivity(
                "Demande de réinitialisation de mot de passe",
                ActivityLog.ActivityType.CONFIGURATION,
                ActivityLog.ActivityStatus.SUCCESS,
                user.getUsername() + " (" + email + ")"
        );
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

        // ✅ Logger l'activité de réinitialisation
        logActivity(
                "Réinitialisation du mot de passe",
                ActivityLog.ActivityType.MODIFICATION,
                ActivityLog.ActivityStatus.SUCCESS,
                user.getUsername()
        );
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

        String changementsEffectues = "";

        if (nom != null && !nom.trim().isEmpty() && !nom.trim().equals(existingUser.getNom())) {
            existingUser.setNom(nom.trim());
            changementsEffectues += "nom, ";
        }
        if (prenom != null && !prenom.trim().isEmpty() && !prenom.trim().equals(existingUser.getPrenom())) {
            existingUser.setPrenom(prenom.trim());
            changementsEffectues += "prénom, ";
        }
        if (email != null && !email.trim().isEmpty() && !email.trim().equals(existingUser.getEmail())) {
            if (userRepo.findByEmail(email.trim()).isPresent() &&
                    !userRepo.findByEmail(email.trim()).get().getId().equals(id)) {
                throw new IllegalArgumentException("L'email '" + email + "' est déjà utilisé par un autre utilisateur.");
            }
            existingUser.setEmail(email.trim());
            changementsEffectues += "email, ";
        }
        if (username != null && !username.trim().isEmpty() && !username.trim().equals(existingUser.getUsername())) {
            if (userRepo.findByUsername(username.trim()).isPresent() &&
                    !userRepo.findByUsername(username.trim()).get().getId().equals(id)) {
                throw new IllegalArgumentException("Le nom d'utilisateur '" + username + "' est déjà pris.");
            }
            existingUser.setUsername(username.trim());
            changementsEffectues += "username, ";
        }
        if (matricule != null && !matricule.trim().isEmpty() && !matricule.trim().equals(existingUser.getMatricule())) {
            if (userRepo.findByMatricule(matricule.trim()).isPresent() &&
                    !userRepo.findByMatricule(matricule.trim()).get().getId().equals(id)) {
                throw new IllegalArgumentException("Le matricule '" + matricule + "' est déjà utilisé par un autre utilisateur.");
            }
            existingUser.setMatricule(matricule.trim());
            changementsEffectues += "matricule, ";
        } else if (matricule != null && matricule.trim().isEmpty() && existingUser.getMatricule() != null) {
            existingUser.setMatricule(null);
            changementsEffectues += "matricule supprimé, ";
        }

        if (actif != null) {
            if (actif) {
                existingUser.setEtatCompte(Utilisateur.EtatCompte.ACTIF);
                changementsEffectues += "compte activé, ";
            } else {
                existingUser.setEtatCompte(Utilisateur.EtatCompte.BLOQUE);
                changementsEffectues += "compte bloqué, ";
            }
        }

        existingUser.setDateModification(LocalDateTime.now());
        Utilisateur updatedUser = userRepo.save(existingUser);

        // ✅ Logger l'activité de modification
        if (!changementsEffectues.isEmpty()) {
            changementsEffectues = changementsEffectues.substring(0, changementsEffectues.length() - 2); // Enlever la dernière virgule
            logActivity(
                    "Modification des informations d'un utilisateur (" + changementsEffectues + ")",
                    ActivityLog.ActivityType.MODIFICATION,
                    ActivityLog.ActivityStatus.SUCCESS,
                    existingUser.getUsername()
            );
        }

        return updatedUser;
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

        // ✅ Logger l'activité de réinitialisation par admin
        logActivity(
                "Réinitialisation du mot de passe par l'administrateur",
                ActivityLog.ActivityType.CONFIGURATION,
                ActivityLog.ActivityStatus.SUCCESS,
                user.getUsername()
        );
    }

    @Override
    @Transactional
    public void deleteUtilisateur(Long userId) {
        Utilisateur user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userId));

        String username = user.getUsername();
        String email = user.getEmail();

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

        // ✅ Logger l'activité de suppression
        logActivity(
                "Suppression d'un utilisateur",
                ActivityLog.ActivityType.SUPPRESSION,
                ActivityLog.ActivityStatus.WARNING,
                username + " (" + email + ")"
        );
    }

    // ✅ MÉTHODE UTILITAIRE POUR LOGGER LES ACTIVITÉS
    private void logActivity(String action, ActivityLog.ActivityType type, ActivityLog.ActivityStatus status, String target) {
        try {
            ActivityLog log = new ActivityLog();
            log.setTimestamp(LocalDateTime.now());

            // Récupérer l'utilisateur connecté
            String currentUsername = getCurrentUsername();
            Utilisateur currentUser = getCurrentUser();

            if (currentUser != null) {
                log.setUserId(currentUser.getId());
                log.setUserName(currentUser.getPrenom() + " " + currentUser.getNom());
                log.setUserEmail(currentUser.getEmail());
            } else {
                log.setUserId(0L);
                log.setUserName("Système");
                log.setUserEmail("system@example.com");
            }

            log.setAction(action);
            log.setType(type);
            log.setStatus(status);
            log.setTarget(target);

            // Récupérer l'adresse IP
            log.setIpAddress(getClientIpAddress());

            // Récupérer le User-Agent
            if (request != null) {
                log.setUserAgent(request.getHeader("User-Agent"));
            }

            log.setDetails(action + " - Cible: " + target);

            activityLogService.create(log);
        } catch (Exception e) {
            // Ne pas bloquer l'action principale si le log échoue
            System.err.println("⚠️ Erreur lors du logging de l'activité: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Récupérer le nom d'utilisateur connecté
    private String getCurrentUsername() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            return "Système";
        }
    }

    // Récupérer l'utilisateur connecté complet
    private Utilisateur getCurrentUser() {
        try {
            String username = getCurrentUsername();
            if (username != null && !username.equals("anonymousUser") && !username.equals("Système")) {
                return userRepo.findByUsername(username).orElse(null);
            }
        } catch (Exception e) {
            // Ignorer
        }
        return null;
    }

    // Récupérer l'adresse IP du client
    private String getClientIpAddress() {
        if (request == null) {
            return "0.0.0.0";
        }

        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String remoteAddr = request.getRemoteAddr();
        return remoteAddr != null ? remoteAddr : "0.0.0.0";
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