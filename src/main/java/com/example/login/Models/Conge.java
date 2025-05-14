package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "conge")
@Getter
@Setter
public class Conge {
    
    @Id
    @Column(name = "id_conge")
    private String idConge;
    
    @ManyToOne
    @JoinColumn(name = "id_employe")
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
    private String etatConge; // en attente, validé, refusé
    
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
    
    // Default constructor
    public Conge() {
    }
    
    // Constructor with essential fields
    public Conge(String idConge, EmployeSimple employe, String typeConge, 
                Date dateDebut, Date dateFin, Integer nbJours) {
        this.idConge = idConge;
        this.employe = employe;
        this.typeConge = typeConge;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbJours = nbJours;
        this.etatConge = "en attente";
        this.dateDemande = new Date(); // Current date/time
    }
    
    // Constructor with all fields except relationships
    public Conge(String idConge, String typeConge, Date dateDebut, Date dateFin,
                Integer nbJours, String motif, String pieceJointe,
                String etatConge, String motifRefus, Date dateDemande, Date dateTraitement) {
        this.idConge = idConge;
        this.typeConge = typeConge;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbJours = nbJours;
        this.motif = motif;
        this.pieceJointe = pieceJointe;
        this.etatConge = etatConge;
        this.motifRefus = motifRefus;
        this.dateDemande = dateDemande;
        this.dateTraitement = dateTraitement;
    }
    
    // Full constructor with all fields
    public Conge(String idConge, EmployeSimple employe, String typeConge,
                Date dateDebut, Date dateFin, Integer nbJours, String motif,
                String pieceJointe, String etatConge, String motifRefus,
                Date dateDemande, Date dateTraitement, Rh validePar,
                PeriodePaie periodePaie) {
        this.idConge = idConge;
        this.employe = employe;
        this.typeConge = typeConge;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbJours = nbJours;
        this.motif = motif;
        this.pieceJointe = pieceJointe;
        this.etatConge = etatConge;
        this.motifRefus = motifRefus;
        this.dateDemande = dateDemande;
        this.dateTraitement = dateTraitement;
        this.validePar = validePar;
        this.periodePaie = periodePaie;
    }
    
    @Override
    public String toString() {
        return "Conge{" +
                "idConge='" + idConge + '\'' +
                ", employe=" + (employe != null ? employe.getIdEmploye() : "null") +
                ", typeConge='" + typeConge + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", nbJours=" + nbJours +
                ", etatConge='" + etatConge + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conge)) return false;
        Conge conge = (Conge) o;
        return idConge != null && idConge.equals(conge.idConge);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}