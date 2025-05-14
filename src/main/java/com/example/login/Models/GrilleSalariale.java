package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "grille_salariale")
@Getter
@Setter
public class GrilleSalariale {
    
    @Id
    @Column(name = "id_grille")
    private String idGrille;
    
    @Column(name = "anciennete_min")
    private Integer ancienneteMin;
    
    @Column(name = "salaire_min", precision = 10, scale = 2)
    private BigDecimal salaireMin;
    
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    
    // One-to-many relationship with Echelon
    @OneToMany(mappedBy = "grilleSalariale", cascade = CascadeType.ALL)
    private Set<Echelon> echelons = new HashSet<>();
    
    // Default constructor
    public GrilleSalariale() {
    }
    
    // Constructor with essential fields
    public GrilleSalariale(String idGrille, Integer ancienneteMin, BigDecimal salaireMin) {
        this.idGrille = idGrille;
        this.ancienneteMin = ancienneteMin;
        this.salaireMin = salaireMin;
    }
    
    // Constructor with all fields except collections
    public GrilleSalariale(String idGrille, Integer ancienneteMin, BigDecimal salaireMin,
                          Date dateDebut, Date dateFin) {
        this.idGrille = idGrille;
        this.ancienneteMin = ancienneteMin;
        this.salaireMin = salaireMin;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    
    // Helper methods for bidirectional relationship with Echelon
    public void addEchelon(Echelon echelon) {
        echelons.add(echelon);
        echelon.setGrilleSalariale(this);
    }
    
    public void removeEchelon(Echelon echelon) {
        echelons.remove(echelon);
        echelon.setGrilleSalariale(null);
    }
    
    @Override
    public String toString() {
        return "GrilleSalariale{" +
                "idGrille='" + idGrille + '\'' +
                ", ancienneteMin=" + ancienneteMin +
                ", salaireMin=" + salaireMin +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrilleSalariale)) return false;
        GrilleSalariale that = (GrilleSalariale) o;
        return idGrille != null && idGrille.equals(that.idGrille);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}