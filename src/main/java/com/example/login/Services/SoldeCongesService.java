package com.example.login.Services;

import com.example.login.Models.SoldeConges;
import com.example.login.Repositories.SoldeCongesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SoldeCongesService {

    @Autowired
    private SoldeCongesRepository repository;

    public SoldeConges create(SoldeConges soldeConges) {
        return repository.save(soldeConges);
    }

    public List<SoldeConges> getAll() {
        return repository.findAll();
    }

    public Optional<SoldeConges> getById(String id) {
        return repository.findById(id);
    }

    public List<SoldeConges> getByEmploye(String idEmploye) {
        return repository.findByEmploye_IdEmploye(idEmploye);
    }

    public SoldeConges getByEmployeAndAnnee(String idEmploye, Integer annee) {
        return repository.findByEmploye_IdEmployeAndAnnee(idEmploye, annee);
    }

    public SoldeConges update(String id, SoldeConges updated) {
        updated.setIdSolde(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
