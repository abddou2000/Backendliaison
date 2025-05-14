package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "prime_indemnite_retenue")
@Getter
@Setter
public class PrimeIndemniteRetenue {
    
    @Id
    @Column(name = "id_prime")
    private String idPrime;
    
    @ManyToOne
    @JoinColumn(name = "id_rubrique")
    private RubriquePaie rubriquePaie;
    
    @ManyToOne
    @JoinColumn(name = "id_typeprime")
    private TypePrimeIndemniteRetenue typePrime;
    
    @Column(name = "nom_prime")
    private String nomPrime;
    
    @Column(name = "code_prime")
    private String codePrime;
    
    @Column(name = "rubrique_source")
    private String rubriqueSource;
    
    @Column(name = "valeur_unitaire", precision = 10, scale = 2)
    private BigDecimal valeurUnitaire;
    
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    
    @ManyToOne
    @JoinColumn(name = "id_employe")
    private EmployeSimple employe;
    
    @ManyToOne
    @JoinColumn(name = "id_periode_paie")
    private PeriodePaie periodePaie;
    
    @ManyToOne
    @JoinColumn(name = "id_profil_salarial")
    private ProfilSalarial profilSalarial;
    
    // Default constructor
    public PrimeIndemniteRetenue() {
    }
    
    // Constructor with essential fields
    public PrimeIndemniteRetenue(String idPrime, String nomPrime, String codePrime) {
        this.idPrime = idPrime;
        this.nomPrime = nomPrime;
        this.codePrime = codePrime;
    }
    
    // Constructor with all fields except relationships
    public PrimeIndemniteRetenue(String idPrime, String nomPrime, String codePrime,
                               String rubriqueSource, BigDecimal valeurUnitaire,
                               Date dateDebut, Date dateFin) {
        this.idPrime = idPrime;
        this.nomPrime = nomPrime;
        this.codePrime = codePrime;
        this.rubriqueSource = rubriqueSource;
        this.valeurUnitaire = valeurUnitaire;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    
    // Constructor with all fields including relationships
    public PrimeIndemniteRetenue(String idPrime, RubriquePaie rubriquePaie,
                               TypePrimeIndemniteRetenue typePrime, String nomPrime,
                               String codePrime, String rubriqueSource, BigDecimal valeurUnitaire,
                               Date dateDebut, Date dateFin, EmployeSimple employe,
                               PeriodePaie periodePaie, ProfilSalarial profilSalarial) {
        this.idPrime = idPrime;
        this.rubriquePaie = rubriquePaie;
        this.typePrime = typePrime;
        this.nomPrime = nomPrime;
        this.codePrime = codePrime;
        this.rubriqueSource = rubriqueSource;
        this.valeurUnitaire = valeurUnitaire;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.employe = employe;
        this.periodePaie = periodePaie;
        this.profilSalarial = profilSalarial;
    }
    
    @Override
    public String toString() {
        return "PrimeIndemniteRetenue{" +
                "idPrime='" + idPrime + '\'' +
                ", nomPrime='" + nomPrime + '\'' +
                ", codePrime='" + codePrime + '\'' +
                ", valeurUnitaire=" + valeurUnitaire +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrimeIndemniteRetenue)) return false;
        PrimeIndemniteRetenue that = (PrimeIndemniteRetenue) o;
        return idPrime != null && idPrime.equals(that.idPrime);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}