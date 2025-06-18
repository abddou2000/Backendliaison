package com.example.login.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "type_cotisation")
public class TypeCotisation {

    @Id
    @Column(name = "id_type_cotisation", nullable = false)
    private String idTypeCotisation;

    @Column(name = "nom_cotisation", nullable = false)
    private String nomCotisation;

    @Column(name = "code_cotisation", nullable = false)
    private String codeCotisation;

    @Column(name = "description")
    private String description;

    @Column(name = "date_debut", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @OneToMany(mappedBy = "typeCotisationRef", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Cotisation> cotisations;

    // ========== Getters & Setters ==========

    public String getIdTypeCotisation() {
        return idTypeCotisation;
    }
    public void setIdTypeCotisation(String idTypeCotisation) {
        this.idTypeCotisation = idTypeCotisation;
    }

    public String getNomCotisation() {
        return nomCotisation;
    }
    public void setNomCotisation(String nomCotisation) {
        this.nomCotisation = nomCotisation;
    }

    public String getCodeCotisation() {
        return codeCotisation;
    }
    public void setCodeCotisation(String codeCotisation) {
        this.codeCotisation = codeCotisation;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDebut() {
        return dateDebut;
    }
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public List<Cotisation> getCotisations() {
        return cotisations;
    }
    public void setCotisations(List<Cotisation> cotisations) {
        this.cotisations = cotisations;
    }
}
