package com.example.login.Services;

import com.example.login.Models.FichePaie;
import com.example.login.Repositories.FichePaieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FichePaieService {

    @Autowired
    private FichePaieRepository repository;

    public FichePaie create(FichePaie fichePaie) {
        return repository.save(fichePaie);
    }

    public List<FichePaie> getAll() {
        return repository.findAll();
    }

    public Optional<FichePaie> getById(String id) {
        return repository.findById(id);
    }

    public List<FichePaie> getByEmploye(String idEmploye) {
        return repository.findByEmploye_IdEmploye(idEmploye);
    }

    public List<FichePaie> getByPeriode(String idPeriode) {
        return repository.findByPeriodePaie_IdPeriode(idPeriode);
    }

    public List<FichePaie> getByAnneeAndMois(Integer annee, Integer mois) {
        return repository.findByPeriodeAnneeAndPeriodeMois(annee, mois);
    }

    public FichePaie update(String id, FichePaie updated) {
        updated.setIdFiche(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
