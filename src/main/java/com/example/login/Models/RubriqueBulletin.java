package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "rubrique_bulletin")
@Getter
@Setter
public class RubriqueBulletin {

    @Id
    @Column(name = "id_rubrique_bulletin")
    private String idRubriqueBulletin;

    @Column(name = "code")
    private String code;

    @Column(name = "designation")
    private String designation;

    // Bases
    @Column(name = "base_patronale")
    private Double basePatronale;

    @Column(name = "base_salariale")
    private Double baseSalariale;

    // Taux
    @Column(name = "taux_patronal")
    private Double tauxPatronal;

    @Column(name = "taux_salarial")
    private Double tauxSalarial;

    // Montants
    @Column(name = "montant_patronal")
    private Double montantPatronal;

    @Column(name = "montant_salarial")
    private Double montantSalarial;

    @ManyToOne
    @JoinColumn(name = "id_resultat")
    private ResultatPaie resultatPaie;

    @ManyToOne
    @JoinColumn(name = "id_rubrique_paie")
    private RubriquePaie rubriquePaie;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    // ... constructeurs, toString, equals/hashCode, etc.
}
