package com.example.login.Services;

import com.example.login.Models.PeriodePaie;
import com.example.login.Repositories.PeriodePaieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeriodePaieService {

    @Autowired
    private PeriodePaieRepository repository;

    public PeriodePaie create(PeriodePaie periode) {
        return repository.save(periode);
    }

    public List<PeriodePaie> getAll() {
        return repository.findAll();
    }

    public Optional<PeriodePaie> getById(String id) {
        return repository.findById(id);
    }

    public List<PeriodePaie> getByAnnee(Integer annee) {
        return repository.findByAnnee(annee);
    }

    public List<PeriodePaie> getByEtat(String etat) {
        return repository.findByEtatPeriode(etat);
    }

    public PeriodePaie update(String id, PeriodePaie updated) {
        updated.setIdPeriode(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
