package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "declaration_sociale")
@Getter
@Setter
public class DeclarationSociale {
    
    @Id
    @Column(name = "id_declaration")
    private String idDeclaration;
    
    @ManyToOne
    @JoinColumn(name = "id_periode")
    private PeriodePaie periodePaie;
    
    @Column(name = "type_declaration")
    private String typeDeclaration; // CNSS, IR, CIMR, etc.
    
    @Column(name = "etat_generation")
    private String etatGeneration; // en attente, générée, etc.
    
    @Column(name = "date_generation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateGeneration;
    
    @Column(name = "fichier_genere")
    private String fichierGenere; // Path or reference to generated file
    
    // Default constructor
    public DeclarationSociale() {
    }
    
    // Constructor with essential fields
    public DeclarationSociale(String idDeclaration, PeriodePaie periodePaie, 
                             String typeDeclaration) {
        this.idDeclaration = idDeclaration;
        this.periodePaie = periodePaie;
        this.typeDeclaration = typeDeclaration;
        this.etatGeneration = "en attente";
    }
    
    // Constructor with all fields
    public DeclarationSociale(String idDeclaration, PeriodePaie periodePaie, 
                             String typeDeclaration, String etatGeneration,
                             Date dateGeneration, String fichierGenere) {
        this.idDeclaration = idDeclaration;
        this.periodePaie = periodePaie;
        this.typeDeclaration = typeDeclaration;
        this.etatGeneration = etatGeneration;
        this.dateGeneration = dateGeneration;
        this.fichierGenere = fichierGenere;
    }
    
    @Override
    public String toString() {
        return "DeclarationSociale{" +
                "idDeclaration='" + idDeclaration + '\'' +
                ", periodePaie=" + (periodePaie != null ? periodePaie.getIdPeriode() : "null") +
                ", typeDeclaration='" + typeDeclaration + '\'' +
                ", etatGeneration='" + etatGeneration + '\'' +
                ", dateGeneration=" + dateGeneration +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeclarationSociale)) return false;
        DeclarationSociale that = (DeclarationSociale) o;
        return idDeclaration != null && idDeclaration.equals(that.idDeclaration);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}