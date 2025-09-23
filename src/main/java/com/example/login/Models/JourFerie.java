// src/main/java/com/example/login/Models/JourFerie.java
package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "jour_ferie")
@Getter
@Setter
public class JourFerie {

    @Id
    @Column(name = "id_jour_ferie")
    private String idJourFerie;

    @Column(name = "nom_jour")
    private String nomJour;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @Column(name = "recurrence_annuelle")
    private Boolean recurrenceAnnuelle;

    // Nouveaux champs pour la validité
    @Column(name = "date_debut_validite")
    @Temporal(TemporalType.DATE)
    private Date dateDebutValidite;

    @Column(name = "date_fin_validite")
    @Temporal(TemporalType.DATE)
    private Date dateFinValidite;

    // Constructeur par défaut
    public JourFerie() {}

    // Constructeur avec les champs originaux
    public JourFerie(String idJourFerie, String nomJour, Date dateDebut, Boolean recurrenceAnnuelle) {
        this.idJourFerie = idJourFerie;
        this.nomJour = nomJour;
        this.dateDebut = dateDebut;
        this.recurrenceAnnuelle = recurrenceAnnuelle;
    }

    // Constructeur avec date fin
    public JourFerie(String idJourFerie, String nomJour, Date dateDebut, Date dateFin, Boolean recurrenceAnnuelle) {
        this.idJourFerie = idJourFerie;
        this.nomJour = nomJour;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.recurrenceAnnuelle = recurrenceAnnuelle;
    }

    // Nouveau constructeur complet avec validité
    public JourFerie(String idJourFerie, String nomJour, Date dateDebut, Date dateFin,
                     Boolean recurrenceAnnuelle, Date dateDebutValidite, Date dateFinValidite) {
        this.idJourFerie = idJourFerie;
        this.nomJour = nomJour;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.recurrenceAnnuelle = recurrenceAnnuelle;
        this.dateDebutValidite = dateDebutValidite;
        this.dateFinValidite = dateFinValidite;
    }

    // Méthode utilitaire pour vérifier si le jour férié est valide à une date donnée
    public boolean isValidAt(Date date) {
        if (date == null) return false;

        boolean afterStart = (dateDebutValidite == null) || !date.before(dateDebutValidite);
        boolean beforeEnd = (dateFinValidite == null) || !date.after(dateFinValidite);

        return afterStart && beforeEnd;
    }

    // Méthode utilitaire pour vérifier si le jour férié est actuellement valide
    public boolean isCurrentlyValid() {
        return isValidAt(new Date());
    }
}