package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "type_mesure")
@Getter
@Setter
public class TypeMesure {

    @Id
    @Column(name = "id_typemesure")
    private String idTypeMesure;

    @Column(name = "code")
    private String code;

    @Column(name = "nom")
    private String nom;

    @Column(name = "embauche")
    private Boolean embauche;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @OneToMany(mappedBy = "typeMesure")
    private List<Mesure> mesures;

    public TypeMesure() {}

    public TypeMesure(String idTypeMesure, String code, String nom, Boolean embauche) {
        this.idTypeMesure = idTypeMesure;
        this.code = code;
        this.nom = nom;
        this.embauche = embauche;
    }

    public TypeMesure(String idTypeMesure, String code, String nom, Boolean embauche,
                      Date dateDebut, Date dateFin) {
        this.idTypeMesure = idTypeMesure;
        this.code = code;
        this.nom = nom;
        this.embauche = embauche;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
}