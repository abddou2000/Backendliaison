package com.example.login.Repositories;

import com.example.login.Models.DemandeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeDocumentRepository extends JpaRepository<DemandeDocument, String> {
    List<DemandeDocument> findByEmploye_IdEmploye(String idEmploye);
    List<DemandeDocument> findByTypeDocument(String typeDocument);
    List<DemandeDocument> findByEtatDemande(String etatDemande);
}
