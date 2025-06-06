package com.example.login.Services;

import com.example.login.Models.TypeMesure;
import com.example.login.Repositories.TypeMesureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeMesureService {

    private final TypeMesureRepository repo;

    @Autowired
    public TypeMesureService(TypeMesureRepository repo) {
        this.repo = repo;
    }

    public TypeMesure create(TypeMesure t) {
        return repo.save(t);
    }

    public TypeMesure update(String id, TypeMesure details) {
        Optional<TypeMesure> opt = repo.findById(id);
        if (opt.isEmpty()) return null;
        TypeMesure existing = opt.get();
        existing.setCode(details.getCode());
        existing.setNom(details.getNom());
        existing.setEmbauche(details.getEmbauche());
        return repo.save(existing);
    }

    public boolean delete(String id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    public TypeMesure getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public List<TypeMesure> listAll() {
        return repo.findAll();
    }

    public List<TypeMesure> findByCode(String code) {
        return repo.findByCode(code);
    }

    public List<TypeMesure> findByNom(String nom) {
        return repo.findByNom(nom);
    }

    public List<TypeMesure> findByEmbauche(Boolean embauche) {
        return repo.findByEmbauche(embauche);
    }
}
