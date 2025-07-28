// src/main/java/com/example/login/Services/SocieteService.java
package com.example.login.Services;

import com.example.login.Models.Societe;
import com.example.login.Repositories.SocieteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SocieteService {
    private final SocieteRepository repo;

    @Autowired
    public SocieteService(SocieteRepository repo) {
        this.repo = repo;
    }

    public Societe save(Societe s) {
        return repo.save(s);
    }

    public Societe update(String id, Societe details) {
        Optional<Societe> opt = repo.findById(id);
        if (opt.isEmpty()) return null;
        Societe existing = opt.get();
        existing.setCodeSociete(details.getCodeSociete());
        existing.setRaisonSociale(details.getRaisonSociale());
        existing.setAdresse(details.getAdresse());
        existing.setVille(details.getVille());
        existing.setTelephone(details.getTelephone());
        existing.setEmail(details.getEmail());
        existing.setSiteWeb(details.getSiteWeb());
        existing.setNomBanque(details.getNomBanque());
        existing.setBic(details.getBic());
        existing.setRib(details.getRib());
        existing.setRc(details.getRc());
        existing.setIce(details.getIce());
        existing.setIfFiscal(details.getIfFiscal());
        existing.setCnss(details.getCnss());
        existing.setDateCreation(details.getDateCreation());
        existing.setDateDebut(details.getDateDebut());
        existing.setDateFin(details.getDateFin());
        return repo.save(existing);
    }

    public boolean delete(String id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    public Societe getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public List<Societe> listAll() {
        return repo.findAll();
    }

    public List<Societe> findByCode(String code) {
        return repo.findByCodeSociete(code);
    }

    public List<Societe> findByVille(String ville) {
        return repo.findByVille(ville);
    }

    public List<Societe> findByPeriod(Date start, Date end) {
        return repo.findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(start, end);
    }

    public List<Societe> findStartedAfter(Date date) {
        return repo.findByDateDebutAfter(date);
    }

    public List<Societe> findEndingBefore(Date date) {
        return repo.findByDateFinBefore(date);
    }

    public List<Societe> findByNomBanque(String nomBanque) {
        return repo.findByNomBanque(nomBanque);
    }
}