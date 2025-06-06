package com.example.login.Services;

import com.example.login.Models.Mesure;
import com.example.login.Repositories.MesureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesureService {

    @Autowired
    private MesureRepository repository;

    public Mesure create(Mesure mesure) {
        return repository.save(mesure);
    }

    public List<Mesure> getAll() {
        return repository.findAll();
    }

    public Optional<Mesure> getById(String id) {
        return repository.findById(id);
    }

    public List<Mesure> getByEmploye(String idEmploye) {
        return repository.findByEmploye_IdEmploye(idEmploye);
    }

    public List<Mesure> getByType(String idTypeMesure) {
        return repository.findByTypeMesure_IdTypeMesure(idTypeMesure);
    }

    public Mesure update(String id, Mesure updated) {
        updated.setIdMesure(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
