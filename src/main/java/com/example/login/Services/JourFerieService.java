// src/main/java/com/example/login/Services/JourFerieService.java
package com.example.login.Services;

import com.example.login.Models.JourFerie;
import com.example.login.Repositories.JourFerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JourFerieService {
    private final JourFerieRepository repository;

    @Autowired
    public JourFerieService(JourFerieRepository repository) {
        this.repository = repository;
    }

    public JourFerie create(JourFerie jf) {
        return repository.save(jf);
    }

    public List<JourFerie> getAll() {
        return repository.findAll();
    }

    public Optional<JourFerie> getById(String id) {
        return repository.findById(id);
    }

    public List<JourFerie> getRecurrentHolidays() {
        return repository.findByRecurrenceAnnuelleTrue();
    }

    public JourFerie update(String id, JourFerie updated) {
        updated.setIdJourFerie(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
