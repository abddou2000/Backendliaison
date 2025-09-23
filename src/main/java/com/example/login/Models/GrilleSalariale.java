package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "grille_salariale")
@Getter @Setter
public class GrilleSalariale {

    @Id
    @Column(name = "id_grille")
    private String idGrille;

    @Column(name = "code_grille", nullable = false, unique = true, length = 50)
    private String codeGrille;

    // NOUVEAU CHAMP - NIVEAU
    @Column(name = "niveau", nullable = false)
    private Integer niveau;

    // NOUVEAU CHAMP - ECHELON
    @Column(name = "echelon_grille", nullable = false)
    private Integer echelonGrille;

    @Column(name = "anciennete_min")
    private Integer ancienneteMin;

    @Column(name = "salaire_min", precision = 10, scale = 2)
    private BigDecimal salaireMin;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut")
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date dateFin;

    @OneToMany(mappedBy = "grilleSalariale", cascade = CascadeType.ALL)
    @JsonIgnore // évite la boucle JSON
    private Set<Echelon> echelons = new HashSet<>();

    public GrilleSalariale() {}

    public GrilleSalariale(String idGrille, String codeGrille, Integer niveau, Integer echelonGrille,
                           Integer ancienneteMin, BigDecimal salaireMin) {
        this.idGrille = idGrille;
        this.codeGrille = codeGrille;
        this.niveau = niveau;
        this.echelonGrille = echelonGrille;
        this.ancienneteMin = ancienneteMin;
        this.salaireMin = salaireMin;
    }

    @Override
    public String toString() {
        return "GrilleSalariale{" +
                "idGrille='" + idGrille + '\'' +
                ", codeGrille='" + codeGrille + '\'' +
                ", niveau=" + niveau +
                ", echelonGrille=" + echelonGrille +
                ", ancienneteMin=" + ancienneteMin +
                ", salaireMin=" + salaireMin +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
}