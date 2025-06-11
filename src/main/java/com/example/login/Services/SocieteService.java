package com.example.login.Services;

import com.example.login.models.Societe;
import com.example.login.repositories.SocieteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocieteService {

    private final SocieteRepository societeRepository;

    @Autowired
    public SocieteService(SocieteRepository societeRepository) {
        this.societeRepository = societeRepository;
    }

    // ➕ Créer une société
    public Societe create(Societe societe) {
        return societeRepository.save(societe);
    }

    // 📄 Lister toutes les sociétés
    public List<Societe> findAll() {
        return societeRepository.findAll();
    }

    // 🔍 Trouver une société par ID
    public Optional<Societe> findById(String idSociete) {
        return societeRepository.findById(idSociete);
    }

    // 🔍 Trouver une société par nom
    public Optional<Societe> findByNomSociete(String nomSociete) {
        return societeRepository.findByNomSociete(nomSociete);
    }

    // ✏️ Mettre à jour une société
    public Optional<Societe> update(String idSociete, Societe s) {
        return societeRepository.findById(idSociete).map(existing -> {
            existing.setNomSociete(s.getNomSociete());
            existing.setAdresse(s.getAdresse());
            existing.setVille(s.getVille());
            existing.setIdentifiantFiscal(s.getIdentifiantFiscal());
            existing.setNumeroCnss(s.getNumeroCnss());
            existing.setNumeroIce(s.getNumeroIce());
            existing.setNumeroRc(s.getNumeroRc());
            existing.setDateDebut(s.getDateDebut());
            existing.setDateFin(s.getDateFin());
            existing.setNomBanque(s.getNomBanque());
            existing.setRib(s.getRib());
            existing.setBic(s.getBic());
            return societeRepository.save(existing);
        });
    }

    // 🗑 Supprimer une société
    public boolean delete(String idSociete) {
        return societeRepository.findById(idSociete).map(existing -> {
            societeRepository.delete(existing);
            return true;
        }).orElse(false);
    }
}
