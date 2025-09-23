package com.example.login.Services;

import com.example.login.Models.ParametrageEtat;
import com.example.login.Repositories.ParametrageEtatRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ParametrageEtatService {

    private final ParametrageEtatRepository repo;

    public ParametrageEtatService(ParametrageEtatRepository repo) {
        this.repo = repo;
    }

    // CREATE
    public ParametrageEtat create(ParametrageEtat p) {
        return repo.save(p);
    }

    // READ ALL (avec filtres optionnels)
    public List<ParametrageEtat> search(String nomFormulaire, Boolean visibilite) {
        if (nomFormulaire != null && visibilite != null) {
            return repo.findByNomFormulaireAndVisibilite(nomFormulaire, visibilite);
        } else if (nomFormulaire != null) {
            return repo.findByNomFormulaire(nomFormulaire);
        } else if (visibilite != null) {
            return repo.findByVisibilite(visibilite);
        }
        return repo.findAll();
    }

    // READ BY ID
    public ParametrageEtat getById(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "ParametrageEtat introuvable avec id=" + id));
    }

    // UPDATE
    public ParametrageEtat update(Long id, ParametrageEtat payload) {
        ParametrageEtat existing = getById(id);
        existing.setNomFormulaire(payload.getNomFormulaire());
        existing.setNomChamp(payload.getNomChamp());
        existing.setVisibilite(payload.getVisibilite());
        existing.setRubrique(payload.getRubrique());
        existing.setTypeRubrique(payload.getTypeRubrique());
        return repo.save(existing);
    }

    // DELETE
    public void delete(Long id) {
        ParametrageEtat existing = getById(id);
        repo.delete(existing);
    }
}
