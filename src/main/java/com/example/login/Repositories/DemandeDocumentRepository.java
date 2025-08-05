package com.example.login.Repositories;

import com.example.login.Models.DemandeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeDocumentRepository extends JpaRepository<DemandeDocument, String> {
    // CORRECTED METHOD: Renamed and changed parameter to Long
    List<DemandeDocument> findByEmploye_Id(Long idEmploye);

    List<DemandeDocument> findByTypeDocument(String typeDocument);
    List<DemandeDocument> findByEtatDemande(String etatDemande);
}