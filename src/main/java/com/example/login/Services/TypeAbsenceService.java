package com.example.login.Services;

import com.example.login.Models.TypeAbsence;
import com.example.login.Repositories.TypeAbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeAbsenceService {

    @Autowired
    private TypeAbsenceRepository repository;

    public TypeAbsence create(TypeAbsence typeAbsence) {
        return repository.save(typeAbsence);
    }

    public List<TypeAbsence> getAll() {
        return repository.findAll();
    }

    public Optional<TypeAbsence> getById(String id) {
        return repository.findById(id);
    }

    public TypeAbsence update(String id, TypeAbsence updated) {
        updated.setIdTypeAbsence(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
