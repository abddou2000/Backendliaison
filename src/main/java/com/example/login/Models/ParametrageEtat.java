package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parametrage_etat")
@Getter
@Setter
public class ParametrageEtat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_formulaire", nullable = true, length = 10)
    private String nomFormulaire;

    // ✅ CORRECTION : nullable = false → nullable = true
    @Column(name = "nom_champ", nullable = true, length = 100)
    private String nomChamp;

    @Column(name = "visibilite", nullable = false)
    private Boolean visibilite;

    @Column(name = "rubrique", length = 100)
    private String rubrique;

    @Column(name = "type_rubrique", length = 50)
    private String typeRubrique;

    // Default constructor
    public ParametrageEtat() {}

    // Constructor with all fields
    public ParametrageEtat(String nomFormulaire, String nomChamp, Boolean visibilite,
                           String rubrique, String typeRubrique) {
        this.nomFormulaire = nomFormulaire;
        this.nomChamp = nomChamp;
        this.visibilite = visibilite;
        this.rubrique = rubrique;
        this.typeRubrique = typeRubrique;
    }

    // ✅ NOUVEAU : Constructor sans nomChamp (pour entrées basées sur rubrique uniquement)
    public ParametrageEtat(String nomFormulaire, Boolean visibilite,
                           String rubrique, String typeRubrique) {
        this.nomFormulaire = nomFormulaire;
        this.nomChamp = null;  // nomChamp est null
        this.visibilite = visibilite;
        this.rubrique = rubrique;
        this.typeRubrique = typeRubrique;
    }

    @Override
    public String toString() {
        return "ParametrageEtat{" +
                "id=" + id +
                ", nomFormulaire='" + nomFormulaire + '\'' +
                ", nomChamp='" + nomChamp + '\'' +
                ", visibilite=" + visibilite +
                ", rubrique='" + rubrique + '\'' +
                ", typeRubrique='" + typeRubrique + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParametrageEtat)) return false;
        ParametrageEtat that = (ParametrageEtat) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}