package com.example.login.Services;

import com.example.login.Models.ParametrageJournal;
import com.example.login.Repositories.ParametrageJournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParametrageJournalService {

    @Autowired
    private ParametrageJournalRepository repository;

    public ParametrageJournal create(ParametrageJournal pj) {
        return repository.save(pj);
    }

    public List<ParametrageJournal> getAll() {
        return repository.findAll();
    }

    public Optional<ParametrageJournal> getById(String id) {
        return repository.findById(id);
    }

    public ParametrageJournal update(String id, ParametrageJournal updated) {
        updated.setIdParametrageJournal(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
