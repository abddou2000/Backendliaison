package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "statut_salarial")
@Getter
@Setter
public class StatutSalarial {
    
    @Id
    @Column(name = "id_statut")
    private String idStatut;
    
    @Column(name = "code_statut")
    private String codeStatut;
    
    @Column(name = "nom_statut")
    private String nomStatut;
    
    @Column(name = "description_statut", columnDefinition = "TEXT")
    private String descriptionStatut;
    
    @Column(name = "raison_inactivite")
    private String raisonInactivite;
    
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    
    // One-to-Many relationship with CategorieSalariale
    @OneToMany(mappedBy = "statutSalarial", cascade = CascadeType.ALL)
    private Set<CategorieSalariale> categoriesSalariales = new HashSet<>();
    
    // One-to-Many relationship with Echelon
    @OneToMany(mappedBy = "statutSalarial", cascade = CascadeType.ALL)
    private Set<Echelon> echelons = new HashSet<>();
    
    // One-to-Many relationship with ProfilSalarial
    @OneToMany(mappedBy = "statutSalarial", cascade = CascadeType.ALL)
    private Set<ProfilSalarial> profilsSalariaux = new HashSet<>();
    
    // Default constructor
    public StatutSalarial() {
    }
    
    // Constructor with essential fields
    public StatutSalarial(String idStatut, String codeStatut, String nomStatut) {
        this.idStatut = idStatut;
        this.codeStatut = codeStatut;
        this.nomStatut = nomStatut;
    }
    
    // Constructor with all fields except collections
    public StatutSalarial(String idStatut, String codeStatut, String nomStatut, 
                         String descriptionStatut, String raisonInactivite,
                         Date dateDebut, Date dateFin) {
        this.idStatut = idStatut;
        this.codeStatut = codeStatut;
        this.nomStatut = nomStatut;
        this.descriptionStatut = descriptionStatut;
        this.raisonInactivite = raisonInactivite;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    
    // Helper methods for bidirectional relationship management
    public void addCategorieSalariale(CategorieSalariale categorieSalariale) {
        categoriesSalariales.add(categorieSalariale);
        categorieSalariale.setStatutSalarial(this);
    }
    
    public void removeCategorieSalariale(CategorieSalariale categorieSalariale) {
        categoriesSalariales.remove(categorieSalariale);
        categorieSalariale.setStatutSalarial(null);
    }
    
    public void addEchelon(Echelon echelon) {
        echelons.add(echelon);
        echelon.setStatutSalarial(this);
    }
    
    public void removeEchelon(Echelon echelon) {
        echelons.remove(echelon);
        echelon.setStatutSalarial(null);
    }
    
    public void addProfilSalarial(ProfilSalarial profilSalarial) {
        profilsSalariaux.add(profilSalarial);
        profilSalarial.setStatutSalarial(this);
    }
    
    public void removeProfilSalarial(ProfilSalarial profilSalarial) {
        profilsSalariaux.remove(profilSalarial);
        profilSalarial.setStatutSalarial(null);
    }
    
    @Override
    public String toString() {
        return "StatutSalarial{" +
                "idStatut='" + idStatut + '\'' +
                ", codeStatut='" + codeStatut + '\'' +
                ", nomStatut='" + nomStatut + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatutSalarial)) return false;
        StatutSalarial that = (StatutSalarial) o;
        return idStatut != null && idStatut.equals(that.idStatut);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}