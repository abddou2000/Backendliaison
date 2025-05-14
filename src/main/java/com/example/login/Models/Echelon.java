package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "echelon")
@Getter
@Setter
public class Echelon {
    
    @Id
    @Column(name = "id")
    private String id;
    
    @Column(name = "niveau")
    private Integer niveau;
    
    @Column(name = "echelon")
    private String echelonCode;
    
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    
    @ManyToOne
    @JoinColumn(name = "id_cat")
    private CategorieSalariale categorieSalariale;
    
    @ManyToOne
    @JoinColumn(name = "id_statut")
    private StatutSalarial statutSalarial;
    
    @ManyToOne
    @JoinColumn(name = "id_grillesalariale")
    private GrilleSalariale grilleSalariale;
    
    // Default constructor
    public Echelon() {
    }
    
    // Constructor with essential fields
    public Echelon(String id, Integer niveau, String echelonCode) {
        this.id = id;
        this.niveau = niveau;
        this.echelonCode = echelonCode;
    }
    
    // Constructor with all fields
    public Echelon(String id, Integer niveau, String echelonCode, Date dateDebut, Date dateFin,
                  CategorieSalariale categorieSalariale, StatutSalarial statutSalarial,
                  GrilleSalariale grilleSalariale) {
        this.id = id;
        this.niveau = niveau;
        this.echelonCode = echelonCode;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.categorieSalariale = categorieSalariale;
        this.statutSalarial = statutSalarial;
        this.grilleSalariale = grilleSalariale;
    }
    
    @Override
    public String toString() {
        return "Echelon{" +
                "id='" + id + '\'' +
                ", niveau=" + niveau +
                ", echelonCode='" + echelonCode + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Echelon)) return false;
        Echelon echelon = (Echelon) o;
        return id != null && id.equals(echelon.id);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}