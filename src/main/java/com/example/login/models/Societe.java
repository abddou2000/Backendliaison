package com.example.login.models;

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
    @Column(name = "idSociete") // <-- modifié ici pour correspondre au JoinColumn
    private String idSociete;

    @Column(name = "nom_societe")
    private String nomSociete;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "ville")
    private String ville;

    @Column(name = "identifiant_fiscal")
    private String identifiantFiscal;

    @Column(name = "numero_cnss")
    private String numeroCnss;

    @Column(name = "numero_ice")
    private String numeroIce;

    @Column(name = "numero_rc")
    private String numeroRc;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @Column(name = "nom_banque")
    private String nomBanque;

    @Column(name = "rib")
    private String rib;

    @Column(name = "bic")
    private String bic;

    public Societe() {
    }

    public Societe(String idSociete, String nomSociete, String adresse, String ville) {
        this.idSociete = idSociete;
        this.nomSociete = nomSociete;
        this.adresse = adresse;
        this.ville = ville;
    }

    public Societe(String idSociete, String nomSociete, String adresse, String ville,
                   String identifiantFiscal, String numeroCnss, String numeroIce, String numeroRc,
                   Date dateDebut, Date dateFin, String nomBanque, String rib, String bic) {
        this.idSociete = idSociete;
        this.nomSociete = nomSociete;
        this.adresse = adresse;
        this.ville = ville;
        this.identifiantFiscal = identifiantFiscal;
        this.numeroCnss = numeroCnss;
        this.numeroIce = numeroIce;
        this.numeroRc = numeroRc;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nomBanque = nomBanque;
        this.rib = rib;
        this.bic = bic;
    }

    @Override
    public String toString() {
        return "Societe{" +
                "idSociete='" + idSociete + '\'' +
                ", nomSociete='" + nomSociete + '\'' +
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
