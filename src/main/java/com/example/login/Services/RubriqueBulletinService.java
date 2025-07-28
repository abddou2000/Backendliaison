package com.example.login.Services;

import com.example.login.Models.RubriqueBulletin;
import com.example.login.Repositories.RubriqueBulletinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RubriqueBulletinService {

    @Autowired
    private RubriqueBulletinRepository repository;

    public RubriqueBulletin create(RubriqueBulletin rb) {
        return repository.save(rb);
    }

    public List<RubriqueBulletin> getAll() {
        return repository.findAll();
    }

    public Optional<RubriqueBulletin> getById(String id) {
        return repository.findById(id);
    }

    public RubriqueBulletin update(String id, RubriqueBulletin updated) {
        updated.setIdRubriqueBulletin(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
