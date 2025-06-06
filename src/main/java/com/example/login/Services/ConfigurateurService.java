// src/main/java/com/example/login/Services/ConfigurateurService.java
package com.example.login.Services;

import com.example.login.Controllers.dto.CreateConfigurateurDto;
import com.example.login.Models.Configurateur;
import com.example.login.Models.EmployeSimple;
import com.example.login.Models.Role;
import com.example.login.Repositories.ConfigurateurRepository;
import com.example.login.Repositories.EmployeSimpleRepository;
import com.example.login.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConfigurateurService {

    private final ConfigurateurRepository configurateurRepository;
    private final EmployeSimpleRepository employeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ConfigurateurService(
            ConfigurateurRepository configurateurRepository,
            EmployeSimpleRepository employeRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.configurateurRepository = configurateurRepository;
        this.employeRepository = employeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Récupère la liste de tous les configurateurs en base.
     */
    public List<Configurateur> listConfigurateurs() {
        return configurateurRepository.findAll();
    }

    /**
     * Récupère un configurateur par son ID.
     * Retourne null si non trouvé.
     */
    public Configurateur getConfigurateurById(String id) {
        Optional<Configurateur> optional = configurateurRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * Crée un Configurateur et son EmployeSimple associé à partir d'un DTO « plat ».
     * @param dto Données de création validées
     * @return le Configurateur persistant
     */
    @Transactional
    public Configurateur createFromDto(CreateConfigurateurDto dto) {
        // 1) Récupérer le rôle “CONFIGURATEUR”
        Role role = roleRepository.findByIdRole("CONFIGURATEUR")
                .orElseThrow(() -> new RuntimeException("Role CONFIGURATEUR introuvable"));

        // 2) Générer un UUID commun pour EmployeSimple et Configurateur
        String uniqueId = UUID.randomUUID().toString();

        // 3) Construire l’EmployeSimple
        EmployeSimple employe = new EmployeSimple();
        employe.setIdEmploye(uniqueId);
        employe.setNom(dto.getNom());
        employe.setPrenom(dto.getPrenom());
        employe.setEmailPro(dto.getEmailPro());
        employe.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        employe.setTelephone(dto.getTelephone());
        employe.setAdresse(dto.getAdresse());
        employe.setCin(dto.getCin());
        employe.setNomUtilisateur(dto.getNomUtilisateur());
        employe.setRole(role);
        // Si EmployeSimple possède des dates de création/modification, vous pouvez les initialiser ici
        // employe.setDateCreation(new Timestamp(System.currentTimeMillis()));
        // employe.setDateModification(new Timestamp(System.currentTimeMillis()));

        // 4) Sauver l’EmployeSimple en base
        employeRepository.save(employe);

        // 5) Construire l’entité Configurateur
        Configurateur cfg = new Configurateur();
        cfg.setIdConfigurateur(uniqueId);
        cfg.setIdConfiguration(uniqueId);
        cfg.setNom(dto.getNom());
        cfg.setPrenom(dto.getPrenom());
        cfg.setEmail(dto.getEmailPro());
        cfg.setTelephone(dto.getTelephone());
        cfg.setNomUtilisateur(dto.getNomUtilisateur());
        cfg.setEtatCompte("ACTIF");
        cfg.setDateCreation(new Timestamp(System.currentTimeMillis()));
        cfg.setDateModification(new Timestamp(System.currentTimeMillis()));
        // Si Configurateur possède d’autres relations (administrateurs, societes), elles peuvent être initialisées plus tard

        // 6) Sauver le Configurateur en base
        return configurateurRepository.save(cfg);
    }

    // Vous pouvez ajouter ici d’autres méthodes (update, delete) si nécessaire...
}
