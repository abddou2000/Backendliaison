package com.example.login.Services;

import com.example.login.Models.RubriquePaie;
import com.example.login.Repositories.RubriquePaieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RubriquePaieService {

    private final RubriquePaieRepository repo;

    @Autowired
    public RubriquePaieService(RubriquePaieRepository repo) {
        this.repo = repo;
    }

    public RubriquePaie create(RubriquePaie r) {
        return repo.save(r);
    }

    public RubriquePaie update(String id, RubriquePaie details) {
        Optional<RubriquePaie> opt = repo.findById(id);
        if (opt.isEmpty()) return null;
        RubriquePaie existing = opt.get();
        existing.setCodeRubrique(details.getCodeRubrique());
        existing.setLibelle(details.getLibelle());
        existing.setDescription(details.getDescription());
        existing.setTypeRubrique(details.getTypeRubrique());
        existing.setFormuleCalcul(details.getFormuleCalcul());
        existing.setDateDebut(details.getDateDebut());
        existing.setDateFin(details.getDateFin());
        return repo.save(existing);
    }

    public boolean delete(String id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    public RubriquePaie getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public List<RubriquePaie> listAll() {
        return repo.findAll();
    }

    public List<RubriquePaie> findByCode(String code) {
        return repo.findByCodeRubrique(code);
    }

    public List<RubriquePaie> findByType(String type) {
        return repo.findByTypeRubrique(type);
    }

    public List<RubriquePaie> findByPeriod(Date start, Date end) {
        return repo.findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(start, end);
    }

    public List<RubriquePaie> findEndingAfter(Date date) {
        return repo.findByDateFinAfter(date);
    }

    public List<RubriquePaie> findByLibelleLike(String fragment) {
        return repo.findByLibelleContainingIgnoreCase(fragment);
    }
}
