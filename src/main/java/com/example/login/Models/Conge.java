package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "conge")
@Getter
@Setter
public class Conge {

    @Id
    @Column(name = "id_conge")
    private String idConge;

    @ManyToOne
    @JoinColumn(
            name = "id_employe",           // colonne FK dans conge (snake_case)
            referencedColumnName = "id_user" // PK de employe_simple
    )
    private EmployeSimple employe;

    @Column(name = "type_conge")
    private String typeConge;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @Column(name = "nb_jours")
    private Integer nbJours;

    @Column(name = "motif")
    private String motif;

    @Column(name = "piece_jointe")
    private String pieceJointe;

    @Column(name = "etat_conge")
    private String etatConge;

    @Column(name = "motif_refus")
    private String motifRefus;

    @Column(name = "date_demande")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDemande;

    @Column(name = "date_traitement")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTraitement;

    @ManyToOne
    @JoinColumn(name = "valide_par")
    private Rh validePar;

    @ManyToOne
    @JoinColumn(name = "id_periode")
    private PeriodePaie periodePaie;

    public Conge() {}
    // autres constructeurs et méthodes inchangés
}
