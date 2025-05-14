package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "societe")
@Getter
@Setter
public class Societe {
    
    @Id
   @Column(name = "idSociete")
    private String idSociete;
    
    @Column(name = "raison_sociale")
    private String raisonSociale;
    
    @Column(name = "adresse")
    private String adresse;
    
    @Column(name = "telephone")
    private String telephone;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "site_web")
    private String siteWeb;
    
    @Column(name = "rc")
    private String rc;
    
    @Column(name = "ice")
    private String ice;
    
    @Column(name = "if_fiscal")
    private String ifFiscal;
    
    @Column(name = "cnss")
    private String cnss;
    
    @Column(name = "date_creation")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;
    
    // You can add related entities with proper OneToMany mappings here
    
    // Default constructor
    public Societe() {
    }
    
    // Constructor with required fields
    public Societe(String idSociete, String raisonSociale, String adresse) {
        this.idSociete = idSociete;
        this.raisonSociale = raisonSociale;
        this.adresse = adresse;
    }
    
    // Constructor with all fields except collections
    public Societe(String idSociete, String raisonSociale, String adresse,
                  String telephone, String email, String siteWeb, String rc,
                  String ice, String ifFiscal, String cnss, Date dateCreation) {
        this.idSociete = idSociete;
        this.raisonSociale = raisonSociale;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.siteWeb = siteWeb;
        this.rc = rc;
        this.ice = ice;
        this.ifFiscal = ifFiscal;
        this.cnss = cnss;
        this.dateCreation = dateCreation;
    }
    
    @Override
    public String toString() {
        return "Societe{" +
                "idSociete='" + idSociete + '\'' +
                ", raisonSociale='" + raisonSociale + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Societe)) return false;
        Societe societe = (Societe) o;
        return idSociete != null && idSociete.equals(societe.idSociete);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}