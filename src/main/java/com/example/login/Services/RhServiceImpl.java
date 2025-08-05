package com.example.login.Services;

import com.example.login.Models.Rh;
import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.RhRepository;
import com.example.login.Repositories.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RhServiceImpl implements RhService { // <-- La classe implémente bien l'interface

    private final RhRepository rhRepository;
    private final UtilisateurRepository utilisateurRepository;

    public RhServiceImpl(RhRepository rhRepository, UtilisateurRepository utilisateurRepository) {
        this.rhRepository = rhRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    @Transactional
    public Rh create(Rh rhData) {
        Long userId = rhData.getId();
        if (userId == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur est requis pour créer un profil RH.");
        }

        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userId));

        if (rhRepository.existsById(userId)) {
            throw new IllegalStateException("Un profil RH existe déjà pour cet utilisateur.");
        }

        if (utilisateur.getRole() == null || !"RH".equals(utilisateur.getRole().getType())) {
            throw new IllegalStateException("L'utilisateur " + userId + " n'a pas le rôle RH.");
        }

        rhData.setUtilisateur(utilisateur);
        utilisateur.setRh(rhData);

        utilisateurRepository.save(utilisateur);

        return rhData;
    }

    @Override
    public Optional<Rh> getById(Long id) {
        return rhRepository.findById(id);
    }

    @Override
    public List<Rh> getAll() {
        return rhRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        // La suppression de l'Utilisateur entraînera la suppression du profil Rh grâce à la cascade
        utilisateurRepository.deleteById(id);
    }
}