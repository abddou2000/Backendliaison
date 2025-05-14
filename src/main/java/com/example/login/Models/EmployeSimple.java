package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "employe_simple")
@Getter
@Setter
public class EmployeSimple {

    @Id
    @Column(name = "idEmploye")
    private String idEmploye;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "email_pro", unique = true)
    private String emailPro;

    @Column(name = "email_perso")
    private String emailPerso;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @Column(name = "date_naissance")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "cin")
    private String cin;

    @Column(name = "date_embauche")
    @Temporal(TemporalType.DATE)
    private Date dateEmbauche;

    @Column(name = "date_creation")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    @Column(name = "nom_utilisateur")
    private String nomUtilisateur;

    @ManyToOne
    @JoinColumn(name = "id_unite_organisationnelle")
    private UniteOrganisationnelle uniteOrganisationnelle;

    @ManyToOne
    @JoinColumn(name = "id_societe")
    private Societe societe;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;

    // Default constructor
    public EmployeSimple() {
    }

    // Constructor with required fields
    public EmployeSimple(String idEmploye, String nom, String prenom, String email, String motDePasse) {
        this.idEmploye = idEmploye;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "EmployeSimple{" +
                "idEmploye='" + idEmploye + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeSimple)) return false;
        EmployeSimple that = (EmployeSimple) o;
        return idEmploye != null && idEmploye.equals(that.idEmploye);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}