package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "parametrage_bulletin")
@Getter @Setter
public class ParametrageBulletin {

    @Id
    @Column(name = "id_parametrage_bulletin")
    private String idParametrageBulletin;

    @Column(name = "champ")
    private String champ;

    @Column(name = "affichable")
    private Boolean affichable;

    @ManyToOne
    @JoinColumn(name = "id_societe")
    private Societe societe;

    // N⇔N avec RubriquePaie
    @ManyToMany
    @JoinTable(
            name = "parametrage_bulletin_rubrique",
            joinColumns = @JoinColumn(name = "id_parametrage_bulletin"),
            inverseJoinColumns = @JoinColumn(name = "id_rubrique_paie")
    )
    private Set<RubriquePaie> rubriques = new HashSet<>();

    // N⇔N avec Cotisation
    @ManyToMany
    @JoinTable(
            name = "parametrage_bulletin_cotisation",
            joinColumns = @JoinColumn(name = "id_parametrage_bulletin"),
            inverseJoinColumns = @JoinColumn(name = "id_cotisation")
    )
    private Set<Cotisation> cotisations = new HashSet<>();
}
