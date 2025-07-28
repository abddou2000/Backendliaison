package com.example.login.Services;

import com.example.login.Models.ProfilSalarial;
import com.example.login.Repositories.ProfilSalarialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfilSalarialService {

    @Autowired
    private ProfilSalarialRepository repository;

    // CREATE
    public ProfilSalarial create(ProfilSalarial profil) {
        // Vérifs basiques
        if (profil.getCodeProfil() == null || profil.getCodeProfil().isBlank()) {
            throw new IllegalArgumentException("codeProfil est obligatoire");
        }
        if (repository.existsByCodeProfil(profil.getCodeProfil())) {
            throw new IllegalArgumentException("codeProfil déjà utilisé");
        }
        return repository.save(profil);
    }

    // READ ALL
    public List<ProfilSalarial> getAll() {
        return repository.findAll();
    }

    // READ BY ID
    public Optional<ProfilSalarial> getById(String id) {
        return repository.findById(id);
    }

    // --- NEW : READ BY CODE ---
    public Optional<ProfilSalarial> getByCode(String codeProfil) {
        return repository.findByCodeProfil(codeProfil);
    }

    // READ BY CATEGORIE
    public List<ProfilSalarial> getByCategorie(String idCategorie) {
        return repository.findByCategorieSalariale_IdCategorie(idCategorie);
    }

    // READ BY STATUT
    public List<ProfilSalarial> getByStatut(String idStatut) {
        return repository.findByStatutSalarial_IdStatut(idStatut);
    }

    // UPDATE (PUT complet)
    public ProfilSalarial update(String id, ProfilSalarial updated) {
        ProfilSalarial existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profil introuvable"));

        // Gestion du codeProfil (unicité)
        if (updated.getCodeProfil() != null &&
                !updated.getCodeProfil().equals(existing.getCodeProfil())) {

            if (repository.existsByCodeProfil(updated.getCodeProfil())) {
                throw new IllegalArgumentException("codeProfil déjà utilisé");
            }
            existing.setCodeProfil(updated.getCodeProfil());
        }

        // Mise à jour des autres champs
        existing.setNomProfil(updated.getNomProfil());
        existing.setCategorieSalariale(updated.getCategorieSalariale());
        existing.setStatutSalarial(updated.getStatutSalarial());
        existing.setFonction(updated.getFonction());
        existing.setSalaireBase(updated.getSalaireBase());
        existing.setDateDebut(updated.getDateDebut());
        existing.setDateFin(updated.getDateFin());
        // Si tu veux gérer les primes ici, ajoute la logique nécessaire

        return repository.save(existing);
    }

    // --- NEW : PATCH codeProfil uniquement ---
    public ProfilSalarial updateCode(String id, String newCode) {
        ProfilSalarial existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profil introuvable"));

        if (newCode == null || newCode.isBlank()) {
            throw new IllegalArgumentException("codeProfil est obligatoire");
        }
        if (repository.existsByCodeProfil(newCode) && !newCode.equals(existing.getCodeProfil())) {
            throw new IllegalArgumentException("codeProfil déjà utilisé");
        }

        existing.setCodeProfil(newCode);
        return repository.save(existing);
    }

    // DELETE
    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
