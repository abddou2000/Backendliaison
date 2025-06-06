package com.example.login.Services;

import com.example.login.Models.ParametresSysteme;
import com.example.login.Repositories.ParametresSystemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ParametresSystemeService {

    @Autowired
    private ParametresSystemeRepository repository;

    public ParametresSysteme create(ParametresSysteme param) {
        param.setDateModification(new Date());
        return repository.save(param);
    }

    public List<ParametresSysteme> getAll() {
        return repository.findAll();
    }

    public Optional<ParametresSysteme> getById(String id) {
        return repository.findById(id);
    }

    public ParametresSysteme update(String id, ParametresSysteme updated) {
        updated.setIdParametre(id);
        updated.setDateModification(new Date());
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
