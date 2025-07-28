package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;            // <-- import ajouté
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "statut_salarial")
@Getter
@Setter
public class StatutSalarial {

    @Id
    @Column(name = "id_statut")
    private String idStatut;

    @Column(name = "code_statut")
    private String codeStatut;

    @Column(name = "nom_statut")
    private String nomStatut;

    @Column(name = "description_statut", columnDefinition = "TEXT")
    private String descriptionStatut;

    @Column(name = "raison_inactivite")
    private String raisonInactivite;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @OneToMany(mappedBy = "statutSalarial", cascade = CascadeType.ALL)
    @JsonIgnore                                   // <-- évite boucle
    private Set<CategorieSalariale> categoriesSalariales = new HashSet<>();

    @OneToMany(mappedBy = "statutSalarial", cascade = CascadeType.ALL)
    @JsonIgnore                                   // <-- évite boucle
    private Set<Echelon> echelons = new HashSet<>();

    @OneToMany(mappedBy = "statutSalarial", cascade = CascadeType.ALL)
    @JsonIgnore                                   // <-- évite boucle
    private Set<ProfilSalarial> profilsSalariaux = new HashSet<>();

    public StatutSalarial() {
    }

    public StatutSalarial(String idStatut, String codeStatut, String nomStatut) {
        this.idStatut = idStatut;
        this.codeStatut = codeStatut;
        this.nomStatut = nomStatut;
    }

    public StatutSalarial(String idStatut, String codeStatut, String nomStatut,
                          String descriptionStatut, String raisonInactivite,
                          Date dateDebut, Date dateFin) {
        this.idStatut = idStatut;
        this.codeStatut = codeStatut;
        this.nomStatut = nomStatut;
        this.descriptionStatut = descriptionStatut;
        this.raisonInactivite = raisonInactivite;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // Helpers...

    @Override
    public String toString() {
        return "StatutSalarial{" +
                "idStatut='" + idStatut + '\'' +
                ", codeStatut='" + codeStatut + '\'' +
                ", nomStatut='" + nomStatut + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatutSalarial)) return false;
        StatutSalarial that = (StatutSalarial) o;
        return idStatut != null && idStatut.equals(that.idStatut);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
 