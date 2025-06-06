package com.example.login.Services;

import com.example.login.Models.Echelon;
import com.example.login.Repositories.EchelonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EchelonService {

    @Autowired
    private EchelonRepository repository;

    public Echelon create(Echelon echelon) {
        return repository.save(echelon);
    }

    public List<Echelon> getAll() {
        return repository.findAll();
    }

    public Optional<Echelon> getById(String id) {
        return repository.findById(id);
    }

    public List<Echelon> getByCategorie(String idCategorie) {
        return repository.findByCategorieSalariale_IdCategorie(idCategorie);
    }

    public List<Echelon> getByStatut(String idStatut) {
        return repository.findByStatutSalarial_IdStatut(idStatut);
    }

    public List<Echelon> getByGrille(String idGrille) {
        return repository.findByGrilleSalariale_IdGrille(idGrille);
    }

    public Echelon update(String id, Echelon updated) {
        updated.setId(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
