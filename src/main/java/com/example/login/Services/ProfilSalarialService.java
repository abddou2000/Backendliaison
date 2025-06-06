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

    public ProfilSalarial create(ProfilSalarial profil) {
        return repository.save(profil);
    }

    public List<ProfilSalarial> getAll() {
        return repository.findAll();
    }

    public Optional<ProfilSalarial> getById(String id) {
        return repository.findById(id);
    }

    public List<ProfilSalarial> getByCategorie(String idCategorie) {
        return repository.findByCategorieSalariale_IdCategorie(idCategorie);
    }

    public List<ProfilSalarial> getByStatut(String idStatut) {
        return repository.findByStatutSalarial_IdStatut(idStatut);
    }

    public ProfilSalarial update(String id, ProfilSalarial updated) {
        updated.setIdProfil(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
