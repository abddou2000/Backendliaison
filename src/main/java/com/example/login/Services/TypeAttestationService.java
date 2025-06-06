package com.example.login.Services;

import com.example.login.Models.TypeAttestation;
import com.example.login.Repositories.TypeAttestationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeAttestationService {

    private final TypeAttestationRepository repo;

    @Autowired
    public TypeAttestationService(TypeAttestationRepository repo) {
        this.repo = repo;
    }

    public TypeAttestation create(TypeAttestation t) {
        return repo.save(t);
    }

    public TypeAttestation update(String id, TypeAttestation details) {
        return repo.findById(id)
                .map(existing -> {
                    existing.setNomTypeAttestation(details.getNomTypeAttestation());
                    existing.setDescription(details.getDescription());
                    return repo.save(existing);
                })
                .orElse(null);
    }

    public boolean delete(String id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    public TypeAttestation getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public List<TypeAttestation> listAll() {
        return repo.findAll();
    }

    public TypeAttestation findByName(String nom) {
        return repo.findByNomTypeAttestation(nom).orElse(null);
    }
}
