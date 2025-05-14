package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "type_prime_indemnite_retenue")
@Getter
@Setter
public class TypePrimeIndemniteRetenue {
    
    @Id
    @Column(name = "id_typeprime")
    private String idTypePrime;
    
    @Column(name = "type")
    private String type; // "Prime", "Indemnité", "Retenue"
    
    @Column(name = "unite")
    private String unite; // "Nombre", "Montant fixe", "Pourcentage"
    
    @Column(name = "nombre")
    private Integer nombre;
    
    @Column(name = "montant_fixe", precision = 10, scale = 2)
    private BigDecimal montantFixe;
    
    @Column(name = "taux_pourcentage", precision = 5, scale = 2)
    private BigDecimal tauxPourcentage;
    
    @Column(name = "soumis_cnss")
    private Boolean soumisCNSS;
    
    @Column(name = "soumis_amo")
    private Boolean soumisAMO;
    
    @Column(name = "soumis_cimr")
    private Boolean soumisCIMR;
    
    @Column(name = "soumis_ir")
    private Boolean soumisIR;
    
    @OneToMany(mappedBy = "typePrime", cascade = CascadeType.ALL)
    private Set<PrimeIndemniteRetenue> primes = new HashSet<>();
    
    // Default constructor
    public TypePrimeIndemniteRetenue() {
    }
    
    // Constructor with essential fields
    public TypePrimeIndemniteRetenue(String idTypePrime, String type, String unite) {
        this.idTypePrime = idTypePrime;
        this.type = type;
        this.unite = unite;
        this.soumisCNSS = false;
        this.soumisAMO = false;
        this.soumisCIMR = false;
        this.soumisIR = false;
    }
    
    // Full constructor
    public TypePrimeIndemniteRetenue(String idTypePrime, String type, String unite,
                                   Integer nombre, BigDecimal montantFixe, BigDecimal tauxPourcentage,
                                   Boolean soumisCNSS, Boolean soumisAMO, 
                                   Boolean soumisCIMR, Boolean soumisIR) {
        this.idTypePrime = idTypePrime;
        this.type = type;
        this.unite = unite;
        this.nombre = nombre;
        this.montantFixe = montantFixe;
        this.tauxPourcentage = tauxPourcentage;
        this.soumisCNSS = soumisCNSS;
        this.soumisAMO = soumisAMO;
        this.soumisCIMR = soumisCIMR;
        this.soumisIR = soumisIR;
    }
    
    // Helper methods for bidirectional relationship
    public void addPrimeIndemniteRetenue(PrimeIndemniteRetenue prime) {
        primes.add(prime);
        prime.setTypePrime(this);
    }
    
    public void removePrimeIndemniteRetenue(PrimeIndemniteRetenue prime) {
        primes.remove(prime);
        prime.setTypePrime(null);
    }
    
    @Override
    public String toString() {
        return "TypePrimeIndemniteRetenue{" +
                "idTypePrime='" + idTypePrime + '\'' +
                ", type='" + type + '\'' +
                ", unite='" + unite + '\'' +
                ", soumisCNSS=" + soumisCNSS +
                ", soumisAMO=" + soumisAMO +
                ", soumisCIMR=" + soumisCIMR +
                ", soumisIR=" + soumisIR +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypePrimeIndemniteRetenue)) return false;
        TypePrimeIndemniteRetenue that = (TypePrimeIndemniteRetenue) o;
        return idTypePrime != null && idTypePrime.equals(that.idTypePrime);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}