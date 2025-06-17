package com.example.login.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Cotisation {

    @Id
    private String idCotisation;

    /** Nom de la cotisation */
    private String nom;

    /** Le libellé ou type textuel de la cotisation */
    private String typeCotisation;

    /** Liaison vers le TypeCotisation parent, ignorée en JSON */
    @ManyToOne
    @JoinColumn(name = "idTypeCotisation")
    @JsonIgnore
    private TypeCotisation typeCotisationRef;

    /** Taux prélevé sur le salarié (en pourcentage ou en valeur) */
    private double tauxSalarial;

    /** Taux patronal (en pourcentage ou en valeur) */
    private double tauxPatronal;

    /** Plafond appliqué côté salarié */
    private double plafondSalarial;

    /** Plafond appliqué côté employeur */
    private double plafondPatronal;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    /** Description libre */
    private String description;

    // ========== Getters & Setters ==========

    public String getIdCotisation() {
        return idCotisation;
    }
    public void setIdCotisation(String idCotisation) {
        this.idCotisation = idCotisation;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTypeCotisation() {
        return typeCotisation;
    }
    public void setTypeCotisation(String typeCotisation) {
        this.typeCotisation = typeCotisation;
    }

    public TypeCotisation getTypeCotisationRef() {
        return typeCotisationRef;
    }
    public void setTypeCotisationRef(TypeCotisation typeCotisationRef) {
        this.typeCotisationRef = typeCotisationRef;
    }

    public double getTauxSalarial() {
        return tauxSalarial;
    }
    public void setTauxSalarial(double tauxSalarial) {
        this.tauxSalarial = tauxSalarial;
    }

    public double getTauxPatronal() {
        return tauxPatronal;
    }
    public void setTauxPatronal(double tauxPatronal) {
        this.tauxPatronal = tauxPatronal;
    }

    public double getPlafondSalarial() {
        return plafondSalarial;
    }
    public void setPlafondSalarial(double plafondSalarial) {
        this.plafondSalarial = plafondSalarial;
    }

    public double getPlafondPatronal() {
        return plafondPatronal;
    }
    public void setPlafondPatronal(double plafondPatronal) {
        this.plafondPatronal = plafondPatronal;
    }

    public Date getDateDebut() {
        return dateDebut;
    }
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
