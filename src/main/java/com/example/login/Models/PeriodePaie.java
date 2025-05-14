package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "periode_paie")
@Getter
@Setter
public class PeriodePaie {
    
    @Id
    @Column(name = "id_periode")
    private String idPeriode;
    
    @Column(name = "mois")
    private Integer mois;
    
    @Column(name = "annee")
    private Integer annee;
    
    @Column(name = "etat_periode")
    private String etatPeriode; // "ouverte", "fermée"
    
    // One-to-Many relationship with DeclarationSociale
    @OneToMany(mappedBy = "periodePaie", cascade = CascadeType.ALL)
    private Set<DeclarationSociale> declarationsSociales = new HashSet<>();
    
    // One-to-Many relationship with FichePaie
    @OneToMany(mappedBy = "periodePaie", cascade = CascadeType.ALL)
    private Set<FichePaie> fichesPaie = new HashSet<>();
    
    // One-to-Many relationship with ElementVariablePaie
    @OneToMany(mappedBy = "periodePaie", cascade = CascadeType.ALL)
    private Set<ElementVariablePaie> elementsVariablesPaie = new HashSet<>();
    
    // One-to-Many relationship with Conge
    @OneToMany(mappedBy = "periodePaie", cascade = CascadeType.ALL)
    private Set<Conge> conges = new HashSet<>();
    
    // One-to-Many relationship with Absence
    @OneToMany(mappedBy = "periodePaie", cascade = CascadeType.ALL)
    private Set<Absence> absences = new HashSet<>();
    
    // One-to-Many relationship with PrimeIndemniteRetenue
    @OneToMany(mappedBy = "periodePaie", cascade = CascadeType.ALL)
    private Set<PrimeIndemniteRetenue> primesIndemnites = new HashSet<>();
    
    // Default constructor
    public PeriodePaie() {
    }
    
    // Constructor with essential fields
    public PeriodePaie(String idPeriode, Integer mois, Integer annee) {
        this.idPeriode = idPeriode;
        this.mois = mois;
        this.annee = annee;
        this.etatPeriode = "ouverte"; // Default state is open
    }
    
    // Constructor with all fields
    public PeriodePaie(String idPeriode, Integer mois, Integer annee, String etatPeriode) {
        this.idPeriode = idPeriode;
        this.mois = mois;
        this.annee = annee;
        this.etatPeriode = etatPeriode;
    }
    
    // Helper methods for bidirectional relationships
    
   
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}