package com.example.login.Services;

import com.example.login.Models.TypeAbsence;
import com.example.login.Repositories.TypeAbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TypeAbsenceService {

    private final TypeAbsenceRepository repo;

    @Autowired
    public TypeAbsenceService(TypeAbsenceRepository repo) {
        this.repo = repo;
    }

    public TypeAbsence create(TypeAbsence t) {
        return repo.save(t);
    }

    public List<TypeAbsence> getAll() {
        return repo.findAll();
    }

    public Optional<TypeAbsence> getById(String id) {
        return repo.findById(id);
    }

    public TypeAbsence update(String id, TypeAbsence updated) {
        if (!repo.existsById(id)) return null;
        updated.setIdTypeAbsence(id);
        return repo.save(updated);
    }

    public boolean delete(String id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    public boolean existsById(String id) {
        return repo.existsById(id);
    }

    public List<TypeAbsence> findByRequestable(Boolean essMss) {
        return repo.findByEssMss(essMss);
    }

    public List<TypeAbsence> findByPeriod(Date start, Date end) {
        return repo.findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(start, end);
    }

    public List<TypeAbsence> findStartedAfter(Date date) {
        return repo.findByDateDebutAfter(date);
    }

    public List<TypeAbsence> findEndingBefore(Date date) {
        return repo.findByDateFinBefore(date);
    }
}
