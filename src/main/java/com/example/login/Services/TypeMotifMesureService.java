package com.example.login.Services;

import com.example.login.Models.TypeMotifMesure;
import com.example.login.Repositories.TypeMotifMesureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public TypeMotifMesure update(String id, TypeMotifMesure details) {
        Optional<TypeMotifMesure> opt = repo.findById(id);
        if (opt.isEmpty()) return null;
        TypeMotifMesure existing = opt.get();
        existing.setCode(details.getCode());
        existing.setLibelle(details.getLibelle());
        existing.setDescription(details.getDescription());
        return repo.save(existing);
    }

    public boolean delete(String id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    public TypeMotifMesure getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public List<TypeMotifMesure> listAll() {
        return repo.findAll();
    }

    public List<TypeMotifMesure> findByCode(String code) {
        return repo.findByCode(code);
    }

    public List<TypeMotifMesure> findByLibelle(String libelle) {
        return repo.findByLibelle(libelle);
    }
}
