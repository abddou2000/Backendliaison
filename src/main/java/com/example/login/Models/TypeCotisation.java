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

    @Column(name = "nom_cotisation")
    private String nomCotisation;

    @Column(name = "code_cotisation")
    private String codeCotisation;

    // Nouveau champ pour le code de type de cotisation
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

    @OneToMany(mappedBy = "typeCotisationRef")
    private List<Cotisation> cotisations;

    // Default constructor
    public TypeCotisation() {
    }

    // Constructor with essential fields
    public TypeCotisation(String idTypeCotisation,
                          String nomCotisation,
                          String codeCotisation,
                          Date dateDebut) {
        this.idTypeCotisation = idTypeCotisation;
        this.nomCotisation = nomCotisation;
        this.codeCotisation = codeCotisation;
        this.dateDebut = dateDebut;
    }

    // Constructor with all fields including nouveau codeTypeCotisation
    public TypeCotisation(String idTypeCotisation,
                          String nomCotisation,
                          String codeCotisation,
                          String codeTypeCotisation,
                          String description,
                          Date dateDebut,
                          Date dateFin) {
        this.idTypeCotisation = idTypeCotisation;
        this.nomCotisation = nomCotisation;
        this.codeCotisation = codeCotisation;
        this.codeTypeCotisation = codeTypeCotisation;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // Create a new contribution type (placeholder)
    public static TypeCotisation createTypeCotisation(TypeCotisation data) {
        return data;
    }

    // Update existing contribution type (placeholder)
    public static TypeCotisation updateTypeCotisation(String id, TypeCotisation data) {
        data.setIdTypeCotisation(id);
        return data;
    }

    // Delete a contribution type (placeholder)
    public static boolean deleteTypeCotisation(String id) {
        return false;
    }

    // Get a contribution type by ID (placeholder)
    public static TypeCotisation getTypeCotisationById(String id) {
        return null;
    }

    // List all contribution types (placeholder)
    public static List<TypeCotisation> listTypeCotisations() {
        return null;
    }

    @Override
    public String toString() {
        return "TypeCotisation{" +
                "idTypeCotisation='" + idTypeCotisation + '\'' +
                ", nomCotisation='" + nomCotisation + '\'' +
                ", codeCotisation='" + codeCotisation + '\'' +
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
