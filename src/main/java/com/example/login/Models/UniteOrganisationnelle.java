package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "unite_organisationnelle")
@Getter
@Setter
public class UniteOrganisationnelle {

    @Id
    @Column(name = "id_unite")
    private String idUnite;

    @Column(name = "code_unite")
    private String codeUnite;

    @Column(name = "nom_unite")
    private String nomUnite;

    @Column(name = "type_unite")
    private String typeUnite; // "Département", "Service", "Direction", etc.

    // Relation parent : évite la boucle infinie avec @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rattachement")
    @JsonBackReference
    private UniteOrganisationnelle uniteParent;

    // Relation enfants : sérialise les sous-unités avec @JsonManagedReference
    @OneToMany(mappedBy = "uniteParent", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<UniteOrganisationnelle> sousUnites = new HashSet<>();

    @Column(name = "description_unite", columnDefinition = "TEXT")
    private String descriptionUnite;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_societe")
    private Societe societe;

    // Ignore la collection d'employés pour éviter les boucles infinies
    // Si vous voulez sérialiser les employés, utilisez un DTO ou @JsonManagedReference
    @OneToMany(mappedBy = "uniteOrganisationnelle", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<EmployeSimple> employes = new HashSet<>();

    // Default constructor
    public UniteOrganisationnelle() {
    }

    // Constructor with essential fields
    public UniteOrganisationnelle(String idUnite, String codeUnite, String nomUnite, String typeUnite) {
        this.idUnite = idUnite;
        this.codeUnite = codeUnite;
        this.nomUnite = nomUnite;
        this.typeUnite = typeUnite;
    }

    // Constructor with all fields except collections
    public UniteOrganisationnelle(String idUnite, String codeUnite, String nomUnite,
                                  String typeUnite, UniteOrganisationnelle uniteParent,
                                  String descriptionUnite, Date dateDebut, Date dateFin,
                                  Societe societe) {
        this.idUnite = idUnite;
        this.codeUnite = codeUnite;
        this.nomUnite = nomUnite;
        this.typeUnite = typeUnite;
        this.uniteParent = uniteParent;
        this.descriptionUnite = descriptionUnite;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.societe = societe;
    }

    // Helper methods for bidirectional relationship management
    public void addSousUnite(UniteOrganisationnelle sousUnite) {
        sousUnites.add(sousUnite);
        sousUnite.setUniteParent(this);
    }

    public void removeSousUnite(UniteOrganisationnelle sousUnite) {
        sousUnites.remove(sousUnite);
        sousUnite.setUniteParent(null);
    }

    @Override
    public String toString() {
        return "UniteOrganisationnelle{" +
                "idUnite='" + idUnite + '\'' +
                ", codeUnite='" + codeUnite + '\'' +
                ", nomUnite='" + nomUnite + '\'' +
                ", typeUnite='" + typeUnite + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UniteOrganisationnelle)) return false;
        UniteOrganisationnelle unite = (UniteOrganisationnelle) o;
        return idUnite != null && idUnite.equals(unite.idUnite);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}