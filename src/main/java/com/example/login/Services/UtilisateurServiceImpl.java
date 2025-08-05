package com.example.login.Services;

import com.example.login.Models.Role;
import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    private final UtilisateurRepository userRepo;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    public UtilisateurServiceImpl(UtilisateurRepository userRepo,
                                  RoleService roleService,
                                  PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.encoder = encoder;
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
        // 1. Récupérer le rôle demandé
        Role role = roleService.getByType(roleType);
        user.setRole(role);

        // 2. Crypter le mot de passe (en utilisant getPassword() et en le stockant dans passwordHash)
        user.setPasswordHash(encoder.encode(user.getPassword()));

        // 3. Définir la date de création
        user.setDateCreation(LocalDateTime.now());

        // 4. (CORRECTION) Définir un état de compte par défaut pour le nouvel utilisateur
        user.setEtatCompte("ACTIF");

        // 5. Sauvegarder l'utilisateur en base de données
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public void updatePassword(Long userId, String rawPassword) {
        Utilisateur u = getById(userId);
        u.setPasswordHash(encoder.encode(rawPassword));
        userRepo.save(u);
    }
}