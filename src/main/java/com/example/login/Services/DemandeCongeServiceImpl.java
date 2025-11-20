package com.example.login.Services;

import com.example.login.Models.DemandeConge;
import com.example.login.Models.EmployeSimple;
import com.example.login.Repositories.DemandeCongeRepository;
import com.example.login.Repositories.EmployeSimpleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class DemandeCongeServiceImpl implements DemandeCongeService {

    private final DemandeCongeRepository demandeCongeRepository;
    private final EmployeSimpleRepository employeSimpleRepository;

    public DemandeCongeServiceImpl(DemandeCongeRepository demandeCongeRepository,
                                   EmployeSimpleRepository employeSimpleRepository) {
        this.demandeCongeRepository = demandeCongeRepository;
        this.employeSimpleRepository = employeSimpleRepository;
    }

    @Override
    public DemandeConge creerDemande(Long idEmploye,
                                     DemandeConge.TypeConge typeConge,
                                     LocalDate dateDebut,
                                     LocalDate dateFin,
                                     String motif,
                                     String pieceJointe) {

        // 🔹 idEmploye = id de EmployeSimple (même que Utilisateur.id)
        EmployeSimple employe = employeSimpleRepository.findById(idEmploye)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé avec id " + idEmploye));

        DemandeConge demande = new DemandeConge();
        demande.setEmploye(employe);
        demande.setTypeConge(typeConge);
        demande.setDateDebut(dateDebut);
        demande.setDateFin(dateFin);
        demande.setMotif(motif);
        demande.setPieceJointe(pieceJointe);
        demande.setStatut(DemandeConge.StatutDemandeConge.EN_ATTENTE);
        demande.setDateCreation(LocalDateTime.now());

        // 🔹 Calcul du nombre de jours (inclusif)
        if (dateDebut != null && dateFin != null && !dateFin.isBefore(dateDebut)) {
            int nbJours = (int) ChronoUnit.DAYS.between(dateDebut, dateFin) + 1;
            demande.setNombreJours(nbJours);
        }

        return demandeCongeRepository.save(demande);
    }

    @Override
    public List<DemandeConge> getAll() {
        return demandeCongeRepository.findAll();
    }

    @Override
    public DemandeConge getById(Long id) {
        return demandeCongeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande de congé non trouvée avec id " + id));
    }

    @Override
    public List<DemandeConge> getByEmploye(Long idEmploye) {
        // ✅ correspond à findByEmploye_Id(...) dans le repository
        return demandeCongeRepository.findByEmploye_Id(idEmploye);
    }

    @Override
    public List<DemandeConge> getByType(DemandeConge.TypeConge typeConge) {
        return demandeCongeRepository.findByTypeConge(typeConge);
    }

    @Override
    public List<DemandeConge> getByStatut(DemandeConge.StatutDemandeConge statut) {
        return demandeCongeRepository.findByStatut(statut);
    }

    @Override
    public DemandeConge changerStatut(Long idDemande,
                                      DemandeConge.StatutDemandeConge nouveauStatut,
                                      String commentaireManager) {

        DemandeConge demande = getById(idDemande);
        demande.setStatut(nouveauStatut);
        demande.setCommentaireManager(commentaireManager);
        demande.setDateDecision(LocalDateTime.now());

        return demandeCongeRepository.save(demande);
    }

    @Override
    public void delete(Long id) {
        if (!demandeCongeRepository.existsById(id)) {
            throw new RuntimeException("Demande de congé non trouvée avec id " + id);
        }
        demandeCongeRepository.deleteById(id);
    }
}
