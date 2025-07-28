// src/main/java/com/example/login/Services/TypeMesureService.java
package com.example.login.Services;

import com.example.login.Models.TypeMesure;
import com.example.login.Repositories.TypeMesureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    public List<TypeMesure> listAll() {
        return repo.findAll();
    }

    public TypeMesure getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public TypeMesure update(String id, TypeMesure details) {
        if (!repo.existsById(id)) return null;
        details.setIdTypeMesure(id);
        return repo.save(details);
    }

    public boolean delete(String id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
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

    // Filtres sur dates
    public List<TypeMesure> findByPeriod(Date start, Date end) {
        return repo.findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(start, end);
    }

    public List<TypeMesure> findStartedAfter(Date date) {
        return repo.findByDateDebutAfter(date);
    }

    public List<TypeMesure> findEndingBefore(Date date) {
        return repo.findByDateFinBefore(date);
    }
}
