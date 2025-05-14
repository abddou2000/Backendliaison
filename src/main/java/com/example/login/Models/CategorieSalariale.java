package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categorie_salariale")
@Getter
@Setter
public class CategorieSalariale {
    
    @Id
    @Column(name = "id_categorie")
    private String idCategorie;
    
    @Column(name = "code_categorie")
    private String codeCategorie;
    
    @Column(name = "nom_categorie")
    private String nomCategorie;
    
    @Column(name = "description_categorie", columnDefinition = "TEXT")
    private String descriptionCategorie;
    
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    
    @ManyToOne
    @JoinColumn(name = "id_statut_salarial")
    private StatutSalarial statutSalarial;
    
    @OneToMany(mappedBy = "categorieSalariale", cascade = CascadeType.ALL)
    private Set<Echelon> echelons = new HashSet<>();
    
    @OneToMany(mappedBy = "categorieSalariale", cascade = CascadeType.ALL)
    private Set<ProfilSalarial> profilsSalariaux = new HashSet<>();
    
    // Default constructor
    public CategorieSalariale() {
    }
    
    // Constructor with essential fields
    public CategorieSalariale(String idCategorie, String codeCategorie, String nomCategorie) {
        this.idCategorie = idCategorie;
        this.codeCategorie = codeCategorie;
        this.nomCategorie = nomCategorie;
    }
    
    // Constructor with all fields except collections
    public CategorieSalariale(String idCategorie, String codeCategorie, String nomCategorie,
                             String descriptionCategorie, Date dateDebut, Date dateFin,
                             StatutSalarial statutSalarial) {
        this.idCategorie = idCategorie;
        this.codeCategorie = codeCategorie;
        this.nomCategorie = nomCategorie;
        this.descriptionCategorie = descriptionCategorie;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statutSalarial = statutSalarial;
    }
    
    // Helper methods to maintain bidirectional relationships
    public void addEchelon(Echelon echelon) {
        echelons.add(echelon);
        echelon.setCategorieSalariale(this);
    }
    
    public void removeEchelon(Echelon echelon) {
        echelons.remove(echelon);
        echelon.setCategorieSalariale(null);
    }
    
    public void addProfilSalarial(ProfilSalarial profilSalarial) {
        profilsSalariaux.add(profilSalarial);
        profilSalarial.setCategorieSalariale(this);
    }
    
    public void removeProfilSalarial(ProfilSalarial profilSalarial) {
        profilsSalariaux.remove(profilSalarial);
        profilSalarial.setCategorieSalariale(null);
    }
    
    @Override
    public String toString() {
        return "CategorieSalariale{" +
                "idCategorie='" + idCategorie + '\'' +
                ", codeCategorie='" + codeCategorie + '\'' +
                ", nomCategorie='" + nomCategorie + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategorieSalariale)) return false;
        CategorieSalariale that = (CategorieSalariale) o;
        return idCategorie != null && idCategorie.equals(that.idCategorie);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}