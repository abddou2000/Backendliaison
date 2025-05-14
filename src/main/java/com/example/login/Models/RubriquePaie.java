package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "rubrique_paie")
@Getter
@Setter
public class RubriquePaie {
    
    @Id
    @Column(name = "id_rubrique")
    private String idRubrique;
    
    @Column(name = "code_rubrique")
    private String codeRubrique;
    
    @Column(name = "libelle")
    private String libelle;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "type_rubrique")
    private String typeRubrique;
    
    @Column(name = "formule_calcul")
    private String formuleCalcul;
    
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    
    @OneToMany(mappedBy = "rubriquePaie")
    private List<PrimeIndemniteRetenue> primesIndemniteRetenueList;
    
    // Default constructor
    public RubriquePaie() {
    }
    
    // Constructor with essential fields
    public RubriquePaie(String idRubrique, String codeRubrique, String libelle, String typeRubrique) {
        this.idRubrique = idRubrique;
        this.codeRubrique = codeRubrique;
        this.libelle = libelle;
        this.typeRubrique = typeRubrique;
        this.dateDebut = new Date();
    }
    
    // Constructor with all fields except collections
    public RubriquePaie(String idRubrique, String codeRubrique, String libelle, 
                       String description, String typeRubrique, String formuleCalcul, 
                       Date dateDebut, Date dateFin) {
        this.idRubrique = idRubrique;
        this.codeRubrique = codeRubrique;
        this.libelle = libelle;
        this.description = description;
        this.typeRubrique = typeRubrique;
        this.formuleCalcul = formuleCalcul;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    
    @Override
    public String toString() {
        return "RubriquePaie{" +
                "idRubrique='" + idRubrique + '\'' +
                ", codeRubrique='" + codeRubrique + '\'' +
                ", libelle='" + libelle + '\'' +
                ", typeRubrique='" + typeRubrique + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RubriquePaie)) return false;
        RubriquePaie that = (RubriquePaie) o;
        return idRubrique != null && idRubrique.equals(that.idRubrique);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}