package com.example.login.Services;

import com.example.login.models.Rh;
import com.example.login.models.EmployeSimple;
import com.example.login.models.Role;
import com.example.login.repositories.RhRepository;
import com.example.login.repositories.RoleRepository;
import com.example.login.Services.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RhService {

    private final RhRepository rhRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    public RhService(RhRepository rhRepo,
                     RoleRepository roleRepo,
                     PasswordEncoder passwordEncoder,
                     AuthenticationService authenticationService) {
        this.rhRepo = rhRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }

    public List<Rh> findAll() {
        return rhRepo.findAll();
    }

    public Rh findById(String id) {
        return rhRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("RH introuvable : " + id));
    }

    @Transactional
    public Rh create(Rh rh) {
        // Encodage du mot de passe
        rh.setMotDePasse(passwordEncoder.encode(rh.getMotDePasse()));
        // Récupération ou création du rôle RH
        Role roleRh = roleRepo.findByNomRole("RH")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setIdRole(UUID.randomUUID().toString());
                    newRole.setNomRole("RH");
                    return roleRepo.save(newRole);
                });
        rh.setRole(roleRh);
        // Persistance de la fiche RH
        return rhRepo.save(rh);
    }

    @Transactional
    public Rh createRh(Rh rh, EmployeSimple employe) {
        // 1) Enregistrer l'EmployeSimple
        authenticationService.registerUser(employe);
        // 2) Créer la fiche RH (avec création automatique du rôle si nécessaire)
        return create(rh);
    }

    @Transactional
    public Rh update(String id, Rh input) {
        Rh existing = findById(id);
        existing.setNom(input.getNom());
        existing.setPrenom(input.getPrenom());
        existing.setEmail(input.getEmail());
        // Si un nouveau mot de passe est fourni, on le ré-encode
        if (input.getMotDePasse() != null && !input.getMotDePasse().isBlank()) {
            existing.setMotDePasse(passwordEncoder.encode(input.getMotDePasse()));
        }
        return rhRepo.save(existing);
    }

    @Transactional
    public void delete(String id) {
        rhRepo.deleteById(id);
    }
}
