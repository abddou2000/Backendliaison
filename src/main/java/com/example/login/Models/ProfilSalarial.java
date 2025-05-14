package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "profil_salarial")
@Getter
@Setter
public class ProfilSalarial {
    
    @Id
    @Column(name = "id_profil")
    private String idProfil;
    
    @Column(name = "nom_profil")
    private String nomProfil;
    
    @ManyToOne
    @JoinColumn(name = "id_categorie_salariale")
    private CategorieSalariale categorieSalariale;
    
    @ManyToOne
    @JoinColumn(name = "id_statut_salarial")
    private StatutSalarial statutSalarial;
    
    @Column(name = "fonction")
    private String fonction;
    
    @Column(name = "salaire_base", precision = 10, scale = 2)
    private BigDecimal salaireBase;
    
    @OneToMany(mappedBy = "profilSalarial", cascade = CascadeType.ALL)
    private Set<PrimeIndemniteRetenue> primes = new HashSet<>();
    
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    
    // Default constructor
    public ProfilSalarial() {
    }
    
    // Constructor with essential fields
    public ProfilSalarial(String idProfil, String nomProfil, BigDecimal salaireBase) {
        this.idProfil = idProfil;
        this.nomProfil = nomProfil;
        this.salaireBase = salaireBase;
    }
    
    // Constructor with all fields except collections
    public ProfilSalarial(String idProfil, String nomProfil, CategorieSalariale categorieSalariale,
                         StatutSalarial statutSalarial, String fonction, BigDecimal salaireBase,
                         Date dateDebut, Date dateFin) {
        this.idProfil = idProfil;
        this.nomProfil = nomProfil;
        this.categorieSalariale = categorieSalariale;
        this.statutSalarial = statutSalarial;
        this.fonction = fonction;
        this.salaireBase = salaireBase;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    
    // Helper methods to maintain bidirectional relationships
 
    
    @Override
    public String toString() {
        return "ProfilSalarial{" +
                "idProfil='" + idProfil + '\'' +
                ", nomProfil='" + nomProfil + '\'' +
                ", fonction='" + fonction + '\'' +
                ", salaireBase=" + salaireBase +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfilSalarial)) return false;
        ProfilSalarial that = (ProfilSalarial) o;
        return idProfil != null && idProfil.equals(that.idProfil);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}