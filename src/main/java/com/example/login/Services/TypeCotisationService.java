// Fichier : TypeCotisationService.java

package com.example.login.services;

import com.example.login.models.TypeCotisation;
import com.example.login.repositories.TypeCotisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TypeCotisationService {

    private final TypeCotisationRepository repository;

    @Autowired
    public TypeCotisationService(TypeCotisationRepository repository) {
        this.repository = repository;
    }

    public TypeCotisation save(TypeCotisation t) {
        return repository.save(t);
    }

    public List<TypeCotisation> listAll() {
        return repository.findAll();
    }

    public TypeCotisation getById(String id) {
        return repository.findById(id).orElse(null);
    }

    public TypeCotisation update(String id, TypeCotisation updated) {
        Optional<TypeCotisation> optional = repository.findById(id);
        if (optional.isEmpty()) return null;

        TypeCotisation existing = optional.get();
        existing.setNomCotisation(updated.getNomCotisation());
        existing.setCodeCotisation(updated.getCodeCotisation());
        existing.setDescription(updated.getDescription());
        existing.setDateDebut(updated.getDateDebut());
        existing.setDateFin(updated.getDateFin());
        return repository.save(existing);
    }

    public boolean delete(String id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    public List<TypeCotisation> findByName(String nom) {
        return repository.findByNomCotisation(nom);
    }

    public List<TypeCotisation> findByCode(String code) {
        return repository.findByCodeCotisation(code);
    }

    public List<TypeCotisation> findByPeriod(Date start, Date end) {
        return repository.findByDateDebutBetween(start, end);
    }

    public List<TypeCotisation> findStartedAfter(Date date) {
        return repository.findByDateDebutAfter(date);
    }

    public List<TypeCotisation> findEndingBefore(Date date) {
        return repository.findByDateFinBefore(date);
    }
}
