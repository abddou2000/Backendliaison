package com.example.login.Services;

import com.example.login.models.EmployeSimple;
import com.example.login.models.Role;
import com.example.login.repositories.EmployeSimpleRepository;
import com.example.login.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthenticationService {

    private final EmployeSimpleRepository employeSimpleRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(
            EmployeSimpleRepository employeSimpleRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.employeSimpleRepository = employeSimpleRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(EmployeSimple employeSimple) {
        // 1) Génération de l'ID si absent
        if (employeSimple.getIdEmploye() == null) {
            employeSimple.setIdEmploye(UUID.randomUUID().toString());
        }

        // 2) Date de création
        if (employeSimple.getDateCreation() == null) {
            employeSimple.setDateCreation(new java.sql.Date(System.currentTimeMillis()));
        }

        // 3) Encodage sécurisé du mot de passe
        String raw = employeSimple.getMotDePasse();
        employeSimple.setMotDePasse(passwordEncoder.encode(raw));

        // 4) Affectation du rôle EMPLOYE
        Role roleEmp = roleRepository
                .findByNomRole("EMPLOYE")
                .orElseThrow(() -> new RuntimeException("Rôle EMPLOYE introuvable en base"));
        employeSimple.setRole(roleEmp);

        // 5) Sauvegarde finale
        employeSimpleRepository.save(employeSimple);
    }
}
