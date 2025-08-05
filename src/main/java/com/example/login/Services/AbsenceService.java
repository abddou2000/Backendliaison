package com.example.login.Services;

import com.example.login.Models.Absence;
import com.example.login.Repositories.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbsenceService {

    @Autowired
    private AbsenceRepository repository;

    public Absence create(Absence absence) {
        return repository.save(absence);
    }

    public List<Absence> getAll() {
        return repository.findAll();
    }

    public Optional<Absence> getById(String id) {
        return repository.findById(id);
    }

    // This method correctly calls the updated repository method
    public List<Absence> getByEmploye(Long idEmploye) {
        return repository.findByEmploye_Id(idEmploye);
    }

    public List<Absence> getByTypeAbsence(String idTypeAbsence) {
        return repository.findByTypeAbsence_IdTypeAbsence(idTypeAbsence);
    }

    public List<Absence> getByPeriode(String idPeriode) {
        return repository.findByPeriodePaie_IdPeriode(idPeriode);
    }

    public Absence update(String id, Absence updated) {
        updated.setIdAbsence(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}