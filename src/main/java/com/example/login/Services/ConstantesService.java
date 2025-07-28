// src/main/java/com/example/login/Services/ConstantesService.java
package com.example.login.Services;

import com.example.login.Models.Constantes;
import com.example.login.Repositories.ConstantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ConstantesService {

    private final ConstantesRepository repo;

    @Autowired
    public ConstantesService(ConstantesRepository repo) {
        this.repo = repo;
    }

    public Constantes create(Constantes c) {
        return repo.save(c);
    }

    public List<Constantes> getAll() {
        return repo.findAll();
    }

    public Optional<Constantes> getById(String id) {
        return repo.findById(id);
    }

    public List<Constantes> getByCode(String code) {
        return repo.findByCodeConst(code);
    }

    public List<Constantes> getByNom(String nom) {
        return repo.findByNomConst(nom);
    }

    public Constantes update(String id, Constantes updated) {
        updated.setIdConst(id);
        return repo.save(updated);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public boolean existsById(String id) {
        return repo.existsById(id);
    }

    public List<Constantes> findByPeriod(Date start, Date end) {
        return repo.findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(start, end);
    }

    public List<Constantes> findStartedAfter(Date date) {
        return repo.findByDateDebutAfter(date);
    }

    public List<Constantes> findEndingBefore(Date date) {
        return repo.findByDateFinBefore(date);
    }
}