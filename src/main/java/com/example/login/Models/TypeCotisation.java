package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "type_cotisation")
@Getter
@Setter
public class TypeCotisation {

    @Id
    @Column(name = "id_type_cotisation")
    private String idTypeCotisation;

    @Column(name = "nom_type_cotisation")
    private String nomTypeCotisation;

    @Column(name = "code_type_cotisation")
    private String codeTypeCotisation;

    @Column(name = "description")
    private String description;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @OneToMany(mappedBy = "typeCotisationRef", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Cotisation> cotisations;

    // Default constructor
    public TypeCotisation() {}

    // Constructor with essential fields
    public TypeCotisation(String idTypeCotisation,
                          String nomTypeCotisation,
                          String codeTypeCotisation,
                          Date dateDebut) {
        this.idTypeCotisation = idTypeCotisation;
        this.nomTypeCotisation = nomTypeCotisation;
        this.codeTypeCotisation = codeTypeCotisation;
        this.dateDebut = dateDebut;
    }

    // Constructor with all fields
    public TypeCotisation(String idTypeCotisation,
                          String nomTypeCotisation,
                          String codeTypeCotisation,
                          String description,
                          Date dateDebut,
                          Date dateFin) {
        this.idTypeCotisation = idTypeCotisation;
        this.nomTypeCotisation = nomTypeCotisation;
        this.codeTypeCotisation = codeTypeCotisation;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "TypeCotisation{" +
                "idTypeCotisation='" + idTypeCotisation + '\'' +
                ", nomTypeCotisation='" + nomTypeCotisation + '\'' +
                ", codeTypeCotisation='" + codeTypeCotisation + '\'' +
                ", description='" + description + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeCotisation)) return false;
        TypeCotisation that = (TypeCotisation) o;
        return idTypeCotisation != null && idTypeCotisation.equals(that.idTypeCotisation);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}