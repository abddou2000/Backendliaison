package com.example.login.Services;

import com.example.login.Models.DemandeDocument;
import com.example.login.Repositories.DemandeDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemandeDocumentService {

    @Autowired
    private DemandeDocumentRepository repository;

    public DemandeDocument create(DemandeDocument demande) {
        return repository.save(demande);
    }

    public List<DemandeDocument> getAll() {
        return repository.findAll();
    }

    public Optional<DemandeDocument> getById(String id) {
        return repository.findById(id);
    }

    // CORRECTED METHOD: Changed to accept Long and call the new repository method
    public List<DemandeDocument> getByEmploye(Long idEmploye) {
        return repository.findByEmploye_Id(idEmploye);
    }

    public List<DemandeDocument> getByType(String typeDocument) {
        return repository.findByTypeDocument(typeDocument);
    }

    public List<DemandeDocument> getByEtat(String etatDemande) {
        return repository.findByEtatDemande(etatDemande);
    }

    public DemandeDocument update(String id, DemandeDocument updated) {
        updated.setIdDemande(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}