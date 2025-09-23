package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "profil_salarial")
@Getter @Setter
public class ProfilSalarial {

    @Id
    @Column(name = "id_profil")
    private String idProfil;

    @Column(name = "code_profil", nullable = false, unique = true, length = 50)
    private String codeProfil;

    @Column(name = "nom_profil")
    private String nomProfil;

    // NOUVEAU CHAMP - CATEGORIE
    @Column(name = "categorie")
    private String categorie;

    // NOUVEAU CHAMP - PRIMES ASSOCIEES
    @Column(name = "primes_associees", precision = 10, scale = 2)
    private BigDecimal primesAssociees;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categorie_salariale")
    @JsonIgnoreProperties({"profilsSalariaux", "echelons", "hibernateLazyInitializer", "handler"})
    private CategorieSalariale categorieSalariale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_statut_salarial")
    @JsonIgnoreProperties({"profilsSalariaux", "categoriesSalariales", "echelons",
            "hibernateLazyInitializer", "handler"})
    private StatutSalarial statutSalarial;

    @Column(name = "fonction")
    private String fonction;

    @Column(name = "salaire_base", precision = 10, scale = 2)
    private BigDecimal salaireBase;

    @OneToMany(mappedBy = "profilSalarial", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<PrimeIndemniteRetenue> primes = new HashSet<>();

    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut")
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date dateFin;

    public ProfilSalarial() {}

    public ProfilSalarial(String idProfil, String codeProfil, String nomProfil, BigDecimal salaireBase) {
        this.idProfil = idProfil;
        this.codeProfil = codeProfil;
        this.nomProfil = nomProfil;
        this.salaireBase = salaireBase;
    }

    public ProfilSalarial(String idProfil, String codeProfil, String nomProfil,
                          CategorieSalariale categorieSalariale, StatutSalarial statutSalarial,
                          String fonction, BigDecimal salaireBase, Date dateDebut, Date dateFin) {
        this.idProfil = idProfil;
        this.codeProfil = codeProfil;
        this.nomProfil = nomProfil;
        this.categorieSalariale = categorieSalariale;
        this.statutSalarial = statutSalarial;
        this.fonction = fonction;
        this.salaireBase = salaireBase;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "ProfilSalarial{" +
                "idProfil='" + idProfil + '\'' +
                ", codeProfil='" + codeProfil + '\'' +
                ", nomProfil='" + nomProfil + '\'' +
                ", categorie='" + categorie + '\'' +
                ", primesAssociees=" + primesAssociees +
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