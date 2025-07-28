// src/main/java/com/example/login/Models/Constantes.java
package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "constantes")
@Getter
@Setter
public class Constantes {

    @Id
    @Column(name = "id_const")
    private String idConst;

    @Column(name = "code_const")
    private String codeConst;

    @Column(name = "nom_const")
    private String nomConst;

    @Column(name = "valeur")
    private String valeur;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    // Default constructor
    public Constantes() {}

    // Constructor without configurateur
    public Constantes(String idConst, String codeConst, String nomConst, String valeur, Date dateDebut) {
        this.idConst = idConst;
        this.codeConst = codeConst;
        this.nomConst = nomConst;
        this.valeur = valeur;
        this.dateDebut = dateDebut;
    }

    // Full constructor without configurateur
    public Constantes(String idConst, String codeConst, String nomConst, String valeur,
                      Date dateDebut, Date dateFin) {
        this.idConst = idConst;
        this.codeConst = codeConst;
        this.nomConst = nomConst;
        this.valeur = valeur;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
}
