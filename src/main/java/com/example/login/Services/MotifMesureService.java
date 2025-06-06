package com.example.login.Services;

import com.example.login.Models.MotifMesure;
import com.example.login.Repositories.MotifMesureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotifMesureService {

    @Autowired
    private MotifMesureRepository repository;

    public MotifMesure create(MotifMesure motif) {
        return repository.save(motif);
    }

    public List<MotifMesure> getAll() {
        return repository.findAll();
    }

    public Optional<MotifMesure> getById(String id) {
        return repository.findById(id);
    }

    public List<MotifMesure> getByMesure(String idMesure) {
        return repository.findByMesure_IdMesure(idMesure);
    }

    public List<MotifMesure> getByTypeMotif(String idTypeMotifMesure) {
        return repository.findByTypeMotifMesure_IdTypeMotifMesure(idTypeMotifMesure);
    }

    public MotifMesure update(String id, MotifMesure updated) {
        updated.setIdMotif(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
