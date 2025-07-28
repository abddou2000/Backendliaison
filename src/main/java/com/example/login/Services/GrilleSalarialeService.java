package com.example.login.Services;

import com.example.login.Models.GrilleSalariale;
import com.example.login.Repositories.GrilleSalarialeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GrilleSalarialeService {

    private final GrilleSalarialeRepository repository;

    public GrilleSalarialeService(GrilleSalarialeRepository repository) {
        this.repository = repository;
    }

    public GrilleSalariale create(GrilleSalariale grille) {
        if (grille.getIdGrille() == null || grille.getIdGrille().isBlank()) {
            grille.setIdGrille(UUID.randomUUID().toString());
        }
        repository.findByCodeGrille(grille.getCodeGrille()).ifPresent(g -> {
            throw new IllegalArgumentException("code_grille déjà utilisé");
        });
        return repository.save(grille);
    }

    public List<GrilleSalariale> getAll() {
        return repository.findAll();
    }

    public Optional<GrilleSalariale> getById(String id) {
        return repository.findById(id);
    }

    public Optional<GrilleSalariale> getByCode(String code) {
        return repository.findByCodeGrille(code);
    }

    public List<GrilleSalariale> getByEchelon(String echelonId) {
        return repository.findByEchelons_Id(echelonId);
    }

    public GrilleSalariale update(String id, GrilleSalariale updated) {
        updated.setIdGrille(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
