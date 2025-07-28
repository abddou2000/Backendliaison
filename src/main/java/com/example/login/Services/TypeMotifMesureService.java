// src/main/java/com/example/login/Services/TypeMotifMesureService.java
package com.example.login.Services;

import com.example.login.Models.TypeMotifMesure;
import com.example.login.Repositories.TypeMotifMesureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TypeMotifMesureService {

    private final TypeMotifMesureRepository repo;

    @Autowired
    public TypeMotifMesureService(TypeMotifMesureRepository repo) {
        this.repo = repo;
    }

    public TypeMotifMesure create(TypeMotifMesure t) {
        return repo.save(t);
    }

    public List<TypeMotifMesure> listAll() {
        return repo.findAll();
    }

    public TypeMotifMesure getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public TypeMotifMesure update(String id, TypeMotifMesure details) {
        if (!repo.existsById(id)) return null;
        details.setIdTypeMotifMesure(id);
        return repo.save(details);
    }

    public boolean delete(String id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    public List<TypeMotifMesure> findByCode(String code) {
        return repo.findByCode(code);
    }

    public List<TypeMotifMesure> findByLibelle(String libelle) {
        return repo.findByLibelle(libelle);
    }

    public List<TypeMotifMesure> findByPeriod(Date start, Date end) {
        return repo.findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(start, end);
    }

    public List<TypeMotifMesure> findStartedAfter(Date date) {
        return repo.findByDateDebutAfter(date);
    }

    public List<TypeMotifMesure> findEndingBefore(Date date) {
        return repo.findByDateFinBefore(date);
    }
}