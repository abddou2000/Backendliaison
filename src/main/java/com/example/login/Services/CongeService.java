package com.example.login.Services;

import com.example.login.Models.Conge;
import com.example.login.Repositories.CongeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CongeService {

    @Autowired
    private CongeRepository repository;

    public Conge create(Conge conge) {
        return repository.save(conge);
    }

    public Conge update(String id, Conge updated) {
        updated.setIdConge(id);
        return repository.save(updated);
    }

    public Optional<Conge> getById(String id) {
        return repository.findById(id);
    }

    /**
     * THIS METHOD HAS BEEN CORRECTED.
     * It now accepts a Long for the employee ID and calls the correct repository method.
     */
    public List<Conge> getByEmploye(Long idEmploye) {
        return repository.findByEmploye_Id(idEmploye);
    }

    public List<Conge> getAll() {
        return repository.findAll();
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}