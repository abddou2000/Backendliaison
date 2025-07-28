package com.example.login.Services;

import com.example.login.Models.ResultatPaie;
import com.example.login.Repositories.ResultatPaieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultatPaieService {

    @Autowired
    private ResultatPaieRepository repository;

    public ResultatPaie create(ResultatPaie resultat) {
        return repository.save(resultat);
    }

    public List<ResultatPaie> getAll() {
        return repository.findAll();
    }

    public Optional<ResultatPaie> getById(String id) {
        return repository.findById(id);
    }

    public List<ResultatPaie> getByEmploye(String employeId) {
        return repository.findByEmploye_IdEmploye(employeId);
    }

    public List<ResultatPaie> getByPeriode(String periodeId) {
        return repository.findByPeriodePaie_IdPeriode(periodeId);
    }

    public List<ResultatPaie> getBySociete(String societeId) {
        return repository.findBySociete_IdSociete(societeId);
    }

    public ResultatPaie update(String id, ResultatPaie updated) {
        updated.setIdResultat(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
