package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "cotisation")
@Getter
@Setter
public class Cotisation {
    
    @Id
    @Column(name = "id_cotisation")
    private String idCotisation;

    @Column(name = "type_cotisation")
    private String typeCotisation;

    @Column(name = "code_cotisation", nullable = false, unique = true)
    private String codeCotisation;

    @Column(name = "nom_cotisation", nullable = false)
    private String nomCotisation;

    @ManyToOne
    @JoinColumn(name = "id_type_cotisation")
    private TypeCotisation typeCotisationRef;
    
    @Column(name = "taux_salarial")
    private Double tauxSalarial;
    
    @Column(name = "taux_patronal")
    private Double tauxPatronal;
    
    @Column(name = "plafond_salarial")
    private Double plafondSalarial;
    
    @Column(name = "plafond_patronal")
    private Double plafondPatronal;
    
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    
    @Column(name = "description")
    private String description;
    
    // Default constructor
    public Cotisation() {
    }
    
    // Constructor with essential fields
    public Cotisation(String idCotisation, String typeCotisation, TypeCotisation typeCotisationRef,
                      Double tauxSalarial, Double tauxPatronal, Date dateDebut) {
        this.idCotisation = idCotisation;
        this.typeCotisation = typeCotisation;
        this.typeCotisationRef = typeCotisationRef;
        this.tauxSalarial = tauxSalarial;
        this.tauxPatronal = tauxPatronal;
        this.dateDebut = dateDebut;
    }
    
    // Constructor with all fields
    public Cotisation(String idCotisation, String typeCotisation, TypeCotisation typeCotisationRef,
                      Double tauxSalarial, Double tauxPatronal, Double plafondSalarial,
                      Double plafondPatronal, Date dateDebut, Date dateFin, String description) {
        this.idCotisation = idCotisation;
        this.typeCotisation = typeCotisation;
        this.typeCotisationRef = typeCotisationRef;
        this.tauxSalarial = tauxSalarial;
        this.tauxPatronal = tauxPatronal;
        this.plafondSalarial = plafondSalarial;
        this.plafondPatronal = plafondPatronal;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description = description;
    }
    
    // Create a new contribution
    public static Cotisation createCotisation(Cotisation data) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return data;
    }
    
  

    @Override
    public String toString() {
        return "Cotisation{" +
                "idCotisation='" + idCotisation + '\'' +
                ", typeCotisation='" + typeCotisation + '\'' +
                ", typeCotisationRef=" + (typeCotisationRef != null ? typeCotisationRef.toString() : "null") +
                ", tauxSalarial=" + tauxSalarial +
                ", tauxPatronal=" + tauxPatronal +
                ", plafondSalarial=" + plafondSalarial +
                ", plafondPatronal=" + plafondPatronal +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", description='" + description + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cotisation)) return false;
        Cotisation that = (Cotisation) o;
        return idCotisation != null && idCotisation.equals(that.idCotisation);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}