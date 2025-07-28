package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "societe")
@Getter
@Setter
public class Societe {

    @Id
    @Column(name = "idSociete")
    private String idSociete;

    @Column(name = "code_societe")
    private String codeSociete;

    @Column(name = "raison_sociale")
    private String raisonSociale;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "ville")
    private String ville;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Column(name = "site_web")
    private String siteWeb;

    @Column(name = "nom_banque")
    private String nomBanque;

    @Column(name = "bic")
    private String bic;

    @Column(name = "rib")
    private String rib;

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

    @Column(name = "dateDebut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "dateFin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    // Default constructor
    public Societe() {}

    // Constructor with essential fields
    public Societe(String idSociete, String codeSociete, String raisonSociale, String adresse) {
        this.idSociete = idSociete;
        this.codeSociete = codeSociete;
        this.raisonSociale = raisonSociale;
        this.adresse = adresse;
    }

    // Constructor with all fields
    public Societe(
            String idSociete,
            String codeSociete,
            String raisonSociale,
            String adresse,
            String telephone,
            String email,
            String siteWeb,
            String nomBanque,
            String bic,
            String rib,
            String rc,
            String ice,
            String ifFiscal,
            String cnss,
            Date dateCreation,
            Date dateDebut,
            Date dateFin
    ) {
        this.idSociete = idSociete;
        this.codeSociete = codeSociete;
        this.raisonSociale = raisonSociale;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.siteWeb = siteWeb;
        this.nomBanque = nomBanque;
        this.bic = bic;
        this.rib = rib;
        this.rc = rc;
        this.ice = ice;
        this.ifFiscal = ifFiscal;
        this.cnss = cnss;
        this.dateCreation = dateCreation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "Societe{" +
                "idSociete='" + idSociete + '\'' +
                ", codeSociete='" + codeSociete + '\'' +
                ", raisonSociale='" + raisonSociale + '\'' +
                ", adresse='" + adresse + '\'' +
                ", ville='" + ville + '\'' +
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
