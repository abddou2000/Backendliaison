package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mesure")
@Getter
@Setter
public class Mesure {

    @Id
    @Column(name = "id_mesure")
    private String idMesure;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private EmployeSimple employe;

    @ManyToOne
    @JoinColumn(name = "id_typemesure")
    private TypeMesure typeMesure;

    @Column(name = "description")
    private String description;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @OneToMany(mappedBy = "mesure")
    private List<MotifMesure> motifMesures;

    // Default constructor
    public Mesure() { }

    // Constructor with essential fields
    public Mesure(String idMesure,
                  EmployeSimple employe,
                  TypeMesure typeMesure,
                  String description,
                  Date dateDebut) {
        this.idMesure    = idMesure;
        this.employe     = employe;
        this.typeMesure  = typeMesure;
        this.description = description;
        this.dateDebut   = dateDebut;
    }

    // Constructor with all fields
    public Mesure(String idMesure,
                  EmployeSimple employe,
                  TypeMesure typeMesure,
                  String description,
                  Date dateDebut,
                  Date dateFin) {
        this(idMesure, employe, typeMesure, description, dateDebut);
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "Mesure{" +
                "idMesure='" + idMesure + '\'' +
                ", employe=" + (employe != null ? employe.getId() : "null") +
                ", typeMesure=" + (typeMesure != null ? typeMesure.getId() : "null") +
                ", description='" + description + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mesure)) return false;
        Mesure that = (Mesure) o;
        return idMesure != null && idMesure.equals(that.idMesure);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
