package com.example.login.Services;

import com.example.login.Models.GrilleSalariale;
import com.example.login.Repositories.GrilleSalarialeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrilleSalarialeService {

    @Autowired
    private GrilleSalarialeRepository repository;

    public GrilleSalariale create(GrilleSalariale grille) {
        return repository.save(grille);
    }

    public List<GrilleSalariale> getAll() {
        return repository.findAll();
    }

    public Optional<GrilleSalariale> getById(String id) {
        return repository.findById(id);
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
