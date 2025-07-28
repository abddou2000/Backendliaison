package com.example.login.Services;

import com.example.login.Models.TypeCotisation;
import com.example.login.Repositories.TypeCotisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TypeCotisationService {

    private final TypeCotisationRepository repo;

    @Autowired
    public TypeCotisationService(TypeCotisationRepository repo) {
        this.repo = repo;
    }

    /** Crée ou met à jour */
    public TypeCotisation save(TypeCotisation t) {
        return repo.save(t);
    }

    /** Met à jour une entité existante */
    public TypeCotisation update(String id, TypeCotisation details) {
        Optional<TypeCotisation> opt = repo.findById(id);
        if (opt.isEmpty()) {
            return null;
        }
        TypeCotisation existing = opt.get();
        existing.setNomCotisation(details.getNomCotisation());
        existing.setCodeCotisation(details.getCodeCotisation());
        // Mise à jour du nouveau champ
        existing.setCodeTypeCotisation(details.getCodeTypeCotisation());
        existing.setDescription(details.getDescription());
        existing.setDateDebut(details.getDateDebut());
        existing.setDateFin(details.getDateFin());
        return repo.save(existing);
    }

    /** Supprime */
    public boolean delete(String id) {
        if (!repo.existsById(id)) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }

    /** Recherche par ID */
    public TypeCotisation getById(String id) {
        return repo.findById(id).orElse(null);
    }

    /** Liste tous */
    public List<TypeCotisation> listAll() {
        return repo.findAll();
    }

    /** Recherche par nom */
    public List<TypeCotisation> findByName(String nom) {
        return repo.findByNomCotisation(nom);
    }

    /** Recherche par code */
    public List<TypeCotisation> findByCode(String code) {
        return repo.findByCodeCotisation(code);
    }

    /** Recherche par code de type de cotisation */
    public List<TypeCotisation> findByCodeTypeCotisation(String codeType) {
        return repo.findByCodeTypeCotisation(codeType);
    }

    /** Recherche par période (dateDebut ≤ start ≤ dateFin) */
    public List<TypeCotisation> findByPeriod(Date start, Date end) {
        return repo.findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(start, end);
    }

    /** Démarrées après une date */
    public List<TypeCotisation> findStartedAfter(Date date) {
        return repo.findByDateDebutAfter(date);
    }

    /** Terminées avant une date */
    public List<TypeCotisation> findEndingBefore(Date date) {
        return repo.findByDateFinBefore(date);
    }
}
