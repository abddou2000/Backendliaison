package com.example.login.Services;

import com.example.login.Models.EmployeSimple;
import com.example.login.Models.Role;
import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.EmployeSimpleRepository;
import com.example.login.Repositories.RoleRepository;
import com.example.login.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class AuthenticationService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final EmployeSimpleRepository employeSimpleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UtilisateurRepository utilisateurRepository,
                                 RoleRepository roleRepository,
                                 EmployeSimpleRepository employeSimpleRepository,
                                 PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.employeSimpleRepository = employeSimpleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Enregistre un nouvel employé simple dans le système :
     * 1) Crée un Utilisateur lié au rôle "EMPLOYE"
     * 2) Crée l'entité EmployeSimple en référant ce même Utilisateur
     *
     * @param employeData objet EmployeSimple contenant :
     *                    - cin, dateNaissance, lieuNaissance, genre
     *                    - un Utilisateur pré-rempli avec username, passwordHash (clair), nom, prenom, email
     */
    @Transactional
    public void registerUser(EmployeSimple employeData) {
        // 1) Charger le rôle "EMPLOYE"
        Role employeRole = roleRepository.findByType("EMPLOYE")
                .orElseThrow(() -> new IllegalStateException("Le rôle EMPLOYE n'a pas été trouvé"));

        // 2) Préparer l'entité Utilisateur
        Utilisateur u = employeData.getUtilisateur();
        if (u == null) {
            throw new IllegalArgumentException("Les informations de l'utilisateur doivent être fournies");
        }
        u.setRole(employeRole);
        // Encoder le mot de passe en BCrypt
        u.setPasswordHash(passwordEncoder.encode(u.getPasswordHash()));
        // Initialiser les dates et l'état du compte
        u.setEtatCompte("ACTIF");
        u.setDateCreation(LocalDateTime.now());
        u.setDateModification(null);

        // Sauvegarder l'utilisateur
        Utilisateur savedUser = utilisateurRepository.save(u);

        // 3) Lier et sauvegarder l'EmployeSimple
        employeData.setUtilisateur(savedUser);
        employeSimpleRepository.save(employeData);
    }
}
