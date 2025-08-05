package com.example.login.Services;

import com.example.login.Models.ElementVariablePaie;
import com.example.login.Repositories.ElementVariablePaieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElementVariablePaieService {

    @Autowired
    private ElementVariablePaieRepository repository;

    public ElementVariablePaie create(ElementVariablePaie element) {
        return repository.save(element);
    }

    public List<ElementVariablePaie> getAll() {
        return repository.findAll();
    }

    public Optional<ElementVariablePaie> getById(Long id) {
        return repository.findById(id);
    }

    // CORRECTED METHOD: Changed to accept Long and call the new repository method
    public List<ElementVariablePaie> getByEmploye(Long idEmploye) {
        return repository.findByEmploye_Id(idEmploye);
    }

    public List<ElementVariablePaie> getByPeriode(String idPeriode) {
        return repository.findByPeriodePaie_IdPeriode(idPeriode);
    }

    public List<ElementVariablePaie> getByRubrique(String idRubrique) {
        return repository.findByRubriquePaie_IdRubrique(idRubrique);
    }

    public ElementVariablePaie update(Long id, ElementVariablePaie updated) {
        updated.setIdElementVariable(id);
        return repository.save(updated);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}