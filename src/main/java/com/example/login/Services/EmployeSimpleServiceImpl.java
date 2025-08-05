package com.example.login.Services;

import com.example.login.Models.EmployeSimple;
import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.EmployeSimpleRepository;
import com.example.login.Repositories.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
// La correction est ici : on déclare que cette classe IMPLÉMENTE l'interface EmployeSimpleService
public class EmployeSimpleServiceImpl implements EmployeSimpleService {

    private final EmployeSimpleRepository employeRepository;
    private final UtilisateurRepository utilisateurRepository;

    // Le constructeur doit prendre UtilisateurRepository pour la nouvelle logique
    public EmployeSimpleServiceImpl(EmployeSimpleRepository employeRepository, UtilisateurRepository utilisateurRepository) {
        this.employeRepository = employeRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public Optional<EmployeSimple> getById(Long id) {
        return employeRepository.findById(id);
    }

    @Override
    @Transactional
    public EmployeSimple create(EmployeSimple employeData) {
        Long userId = employeData.getId();
        if (userId == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur est requis pour créer un profil d'employé.");
        }

        // 1. Chercher l'utilisateur existant.
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userId));

        // 2. Vérifier si un profil n'existe pas déjà.
        if (employeRepository.existsById(userId)) {
            throw new IllegalStateException("Un profil Employé existe déjà pour cet utilisateur.");
        }

        // 3. Vérifier que l'utilisateur a le bon rôle.
        if (utilisateur.getRole() == null || !"EMPLOYE".equals(utilisateur.getRole().getType())) {
            throw new IllegalStateException("L'utilisateur " + userId + " n'est pas un employé.");
        }

        // 4. Lier l'utilisateur au profil et vice-versa
        employeData.setUtilisateur(utilisateur);
        utilisateur.setEmployeSimple(employeData);

        // 5. Sauvegarder l'entité parente (Utilisateur)
        utilisateurRepository.save(utilisateur);

        return employeData;
    }

    @Override
    public List<EmployeSimple> getAll() {
        return employeRepository.findAll();
    }
}