package com.example.login.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.example.login.models.TypeContrat;

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

    @Column(name = "nom_cotisation")
    private String nomCotisation;

    @Column(name = "code_cotisation")
    private String codeCotisation;

    @Column(name = "description")
    private String description;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @OneToMany(mappedBy = "typeCotisationRef", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Cotisation> cotisations;

    // Constructeurs
    public TypeCotisation() {}

    public TypeCotisation(String idTypeCotisation, String nomCotisation,
                          String codeCotisation, Date dateDebut) {
        this.idTypeCotisation = idTypeCotisation;
        this.nomCotisation = nomCotisation;
        this.codeCotisation = codeCotisation;
        this.dateDebut = dateDebut;
    }

    public TypeCotisation(String idTypeCotisation, String nomCotisation,
                          String codeCotisation, String description,
                          Date dateDebut, Date dateFin) {
        this.idTypeCotisation = idTypeCotisation;
        this.nomCotisation = nomCotisation;
        this.codeCotisation = codeCotisation;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "TypeCotisation{" +
                "idTypeCotisation='" + idTypeCotisation + '\'' +
                ", nomCotisation='" + nomCotisation + '\'' +
                ", codeCotisation='" + codeCotisation + '\'' +
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
