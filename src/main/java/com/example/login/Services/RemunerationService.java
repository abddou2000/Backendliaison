package com.example.login.Services;

import com.example.login.Models.Remuneration;
import com.example.login.Repositories.RemunerationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RemunerationService {

    @Autowired
    private RemunerationRepository repository;

    public Remuneration create(Remuneration remuneration) {
        return repository.save(remuneration);
    }

    public Remuneration update(String id, Remuneration updated) {
        updated.setIdRemuneration(id);
        return repository.save(updated);
    }

    public Optional<Remuneration> getById(String id) {
        return repository.findById(id);
    }

    public Optional<Remuneration> getByEmploye(String idEmploye) {
        return repository.findByEmploye_IdEmploye(idEmploye);
    }

    public List<Remuneration> getAll() {
        return repository.findAll();
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}