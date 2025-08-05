package com.example.login.Services;

import com.example.login.Models.Administrateur;
import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.AdministrateurRepository;
import com.example.login.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdministrateurServiceImpl implements AdministrateurService { // On retire le mot-clé 'abstract'

    private final AdministrateurRepository adminRepo;
    private final UtilisateurRepository userRepo;

    @Autowired
    public AdministrateurServiceImpl(AdministrateurRepository adminRepo,
                                     UtilisateurRepository userRepo) {
        this.adminRepo = adminRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<Administrateur> getAllAdministrateurs() {
        return adminRepo.findAll();
    }

    @Override
    public Administrateur getById(Long id) {
        return adminRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Administrateur non trouvé : " + id));
    }

    @Override
    @Transactional
    public Administrateur create(Administrateur adminData) {
        Long userId = adminData.getId();
        if (userId == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur est requis pour créer un profil Administrateur.");
        }

        Utilisateur utilisateur = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userId));

        if (adminRepo.existsById(userId)) {
            throw new IllegalStateException("Un profil Administrateur existe déjà pour cet utilisateur.");
        }

        if (utilisateur.getRole() == null || !"ADMIN".equals(utilisateur.getRole().getType())) {
            throw new IllegalStateException("L'utilisateur " + userId + " n'est pas un administrateur.");
        }

        // On crée une nouvelle instance pour être sûr de l'état
        Administrateur newProfile = new Administrateur();
        newProfile.setId(utilisateur.getId());
        newProfile.setUtilisateur(utilisateur);
        newProfile.setNiveau(adminData.getNiveau());

        // On lie le profil à l'utilisateur et on sauvegarde l'utilisateur (la cascade fait le reste)
        utilisateur.setAdministrateur(newProfile);
        userRepo.save(utilisateur);

        return newProfile;
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }
}