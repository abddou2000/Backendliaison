package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "parametrage_journal")
@Getter @Setter
public class ParametrageJournal {

    @Id
    @Column(name = "id_parametrage_journal")
    private String idParametrageJournal;

    @Column(name = "nom")
    private String nom;

    @Column(name = "affichable")
    private Boolean affichable;

    @ManyToOne
    @JoinColumn(name = "id_societe")
    private Societe societe;

    // N⇔N avec RubriquePaie
    @ManyToMany
    @JoinTable(
            name = "parametrage_journal_rubrique",
            joinColumns = @JoinColumn(name = "id_parametrage_journal"),
            inverseJoinColumns = @JoinColumn(name = "id_rubrique_paie")
    )
    private Set<RubriquePaie> rubriques = new HashSet<>();
}
