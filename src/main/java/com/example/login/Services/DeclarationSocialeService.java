package com.example.login.Services;

import com.example.login.Models.DeclarationSociale;
import com.example.login.Repositories.DeclarationSocialeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeclarationSocialeService {

    @Autowired
    private DeclarationSocialeRepository repository;

    public DeclarationSociale create(DeclarationSociale declaration) {
        return repository.save(declaration);
    }

    public List<DeclarationSociale> getAll() {
        return repository.findAll();
    }

    public Optional<DeclarationSociale> getById(String id) {
        return repository.findById(id);
    }

    public List<DeclarationSociale> getByPeriode(String idPeriode) {
        return repository.findByPeriodePaie_IdPeriode(idPeriode);
    }

    public List<DeclarationSociale> getByType(String typeDeclaration) {
        return repository.findByTypeDeclaration(typeDeclaration);
    }

    public DeclarationSociale update(String id, DeclarationSociale updated) {
        updated.setIdDeclaration(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
