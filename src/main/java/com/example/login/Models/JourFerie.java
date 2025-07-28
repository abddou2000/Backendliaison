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

    public JourFerie() {}

    public JourFerie(String idJourFerie, String nomJour, Date dateDebut, Boolean recurrenceAnnuelle) {
        this.idJourFerie = idJourFerie;
        this.nomJour = nomJour;
        this.dateDebut = dateDebut;
        this.recurrenceAnnuelle = recurrenceAnnuelle;
    }

    public JourFerie(String idJourFerie, String nomJour, Date dateDebut, Date dateFin, Boolean recurrenceAnnuelle) {
        this.idJourFerie = idJourFerie;
        this.nomJour = nomJour;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.recurrenceAnnuelle = recurrenceAnnuelle;
    }
}
