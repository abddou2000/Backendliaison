package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parametrage_etat")
@Getter @Setter
public class ParametrageEtat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_formulaire", nullable = false, length = 10)
    private String nomFormulaire; // BP, JP, AT, AS

    @Column(name = "nom_champ", nullable = false, length = 100)
    private String nomChamp;

    @Column(name = "visibilite", nullable = false)
    private Boolean visibilite;

    @Column(name = "rubrique", length = 100)
    private String rubrique;

    @Column(name = "type_rubrique", length = 50)
    private String typeRubrique;

    public ParametrageEtat() {}

    public ParametrageEtat(String nomFormulaire, String nomChamp, Boolean visibilite,
                           String rubrique, String typeRubrique) {
        this.nomFormulaire = nomFormulaire;
        this.nomChamp = nomChamp;
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
}