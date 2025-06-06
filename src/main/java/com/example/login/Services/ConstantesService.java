package com.example.login.Services;

import com.example.login.Models.Constantes;
import com.example.login.Repositories.ConstantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConstantesService {

    @Autowired
    private ConstantesRepository repository;

    public Constantes create(Constantes constante) {
        return repository.save(constante);
    }

    public List<Constantes> getAll() {
        return repository.findAll();
    }

    public Optional<Constantes> getById(String id) {
        return repository.findById(id);
    }

    public List<Constantes> getByCode(String codeConst) {
        return repository.findByCodeConst(codeConst);
    }

    public List<Constantes> getByConfigurateur(String idConfigurateur) {
        return repository.findByConfigurateur_IdConfigurateur(idConfigurateur);
    }

    public Constantes update(String id, Constantes updated) {
        updated.setIdConst(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
