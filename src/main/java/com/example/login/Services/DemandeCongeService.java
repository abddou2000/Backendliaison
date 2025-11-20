package com.example.login.Services;

import com.example.login.Models.DemandeConge;

import java.time.LocalDate;
import java.util.List;

public interface DemandeCongeService {

    DemandeConge creerDemande(Long idEmploye,
                              DemandeConge.TypeConge typeConge,
                              LocalDate dateDebut,
                              LocalDate dateFin,
                              String motif,
                              String pieceJointe);

    List<DemandeConge> getAll();

    DemandeConge getById(Long id);

    List<DemandeConge> getByEmploye(Long idEmploye);

    List<DemandeConge> getByType(DemandeConge.TypeConge typeConge);

    List<DemandeConge> getByStatut(DemandeConge.StatutDemandeConge statut);

    DemandeConge changerStatut(Long idDemande,
                               DemandeConge.StatutDemandeConge nouveauStatut,
                               String commentaireManager);

    void delete(Long id);
}
