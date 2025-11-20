package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "demande_conge")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DemandeConge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande")
    private Long idDemande;

    // 🔗 L'employé qui fait la demande
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe")
    private EmployeSimple employe;

    // 🔹 Type de congé (Payé, Récupération, etc.)
    @Enumerated(EnumType.STRING)
    @Column(name = "type_conge", nullable = false)
    private TypeConge typeConge;

    // 🔹 Statut de la demande (en attente, validée, refusée…)
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutDemandeConge statut = StatutDemandeConge.EN_ATTENTE;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    // Nombre de jours calculés (pour l’affichage dans ton UI)
    @Column(name = "nombre_jours")
    private Integer nombreJours;

    // Motif éventuel
    @Column(name = "motif", length = 1000)
    private String motif;

    // Chemin / nom du fichier joint (si tu enregistres juste le path)
    @Column(name = "piece_jointe")
    private String pieceJointe;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Column(name = "date_decision")
    private LocalDateTime dateDecision;

    @Column(name = "commentaire_manager")
    private String commentaireManager;

    // 🔹 Enums internes ou séparées selon ton goût
    public enum TypeConge {
        CONGE_PAYE,
        JOURS_RECUPERATION
        // tu pourras ajouter : MALADIE, SANS_SOLDE, etc.
    }

    public enum StatutDemandeConge {
        EN_ATTENTE,
        VALIDEE,
        REFUSEE,
        ANNULEE
    }
}
