package com.example.login.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "rh")
@Getter
@Setter
public class Rh {

    /**
     * Identifiant unique du RH et clé primaire
     */
    @Id
    @Column(name = "id_rh", nullable = false, updatable = false)
    private String idRh;

    /** Nom du responsable RH */
    @Column(name = "nom", nullable = false)
    private String nom;

    /** Prénom du responsable RH */
    @Column(name = "prenom", nullable = false)
    private String prenom;

    /** Email (login) unique du RH */
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /** Mot de passe chiffré */
    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    /** Date de création de l'entrée */
    @Column(name = "date_creation", nullable = false)
    private Date dateCreation;

    /** Rôle associé au RH */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;

    /**
     * Génère idRh et dateCreation avant persistance si absents.
     */
    @PrePersist
    public void ensureIds() {
        if (idRh == null) {
            idRh = UUID.randomUUID().toString();
        }
        if (dateCreation == null) {
            dateCreation = new Date(System.currentTimeMillis());
        }
    }

    public Rh() {
        // constructeur par défaut
    }

    public Rh(String nom, String prenom, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        ensureIds();
    }
}
