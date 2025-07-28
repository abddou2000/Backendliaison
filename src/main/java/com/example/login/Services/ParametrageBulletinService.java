package com.example.login.Services;

import com.example.login.Models.ParametrageBulletin;
import com.example.login.Repositories.ParametrageBulletinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParametrageBulletinService {

    @Autowired
    private ParametrageBulletinRepository repository;

    public ParametrageBulletin create(ParametrageBulletin pb) {
        return repository.save(pb);
    }

    public List<ParametrageBulletin> getAll() {
        return repository.findAll();
    }

    public Optional<ParametrageBulletin> getById(String id) {
        return repository.findById(id);
    }

    public ParametrageBulletin update(String id, ParametrageBulletin updated) {
        updated.setIdParametrageBulletin(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
