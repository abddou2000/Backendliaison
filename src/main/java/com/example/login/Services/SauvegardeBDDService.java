package com.example.login.Services;

import com.example.login.Models.SauvegardeBDD;
import com.example.login.Repositories.SauvegardeBDDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SauvegardeBDDService {

    private final SauvegardeBDDRepository repo;

    @Autowired
    public SauvegardeBDDService(SauvegardeBDDRepository repo) {
        this.repo = repo;
    }

    public SauvegardeBDD create(SauvegardeBDD s) {
        if (s.getDateSauvegarde() == null) {
            s.setDateSauvegarde(new Date());
        }
        return repo.save(s);
    }

    public SauvegardeBDD update(String id, SauvegardeBDD details) {
        Optional<SauvegardeBDD> opt = repo.findById(id);
        if (opt.isEmpty()) return null;
        SauvegardeBDD existing = opt.get();
        existing.setNomFichier(details.getNomFichier());
        existing.setEmplacement(details.getEmplacement());
        existing.setDateSauvegarde(details.getDateSauvegarde());
        existing.setCreePar(details.getCreePar());
        return repo.save(existing);
    }

    public boolean delete(String id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    public SauvegardeBDD getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public List<SauvegardeBDD> listAll() {
        return repo.findAll();
    }

    // CORRECTED METHOD: Changed to accept Long and call the new repository method
    public List<SauvegardeBDD> listByAdmin(Long idAdmin) {
        return repo.findByCreePar_Id(idAdmin);
    }
}