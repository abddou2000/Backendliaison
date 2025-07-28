// src/main/java/com/example/login/Models/TypeAbsence.java
package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "type_absence")
@Getter
@Setter
public class TypeAbsence {

    @Id
    @Column(name = "id_type_absence")
    private String idTypeAbsence;

    @Column(name = "nom_absence")
    private String nomAbsence;

    @Column(name = "code_absence")
    private String codeAbsence;

    @Column(name = "justificatif_requis")
    private Boolean justificatifRequis;

    @Column(name = "absence_remuneree")
    private Boolean absenceRemuneree;

    @Column(name = "impact_solde_conge")
    private Boolean impactSoldeConge;

    @Column(name = "ess_mss")
    private Boolean essMss;  // ← Nouveau champ: peut être demandé par le salarié

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @OneToMany(mappedBy = "typeAbsence", cascade = CascadeType.ALL)
    private Set<Absence> absences = new HashSet<>();

    public TypeAbsence() {
        this.justificatifRequis = false;
        this.absenceRemuneree = false;
        this.impactSoldeConge = false;
        this.essMss = false;
    }

    public TypeAbsence(String idTypeAbsence, String nomAbsence, String codeAbsence) {
        this();
        this.idTypeAbsence = idTypeAbsence;
        this.nomAbsence = nomAbsence;
        this.codeAbsence = codeAbsence;
    }

    public TypeAbsence(String idTypeAbsence, String nomAbsence, String codeAbsence,
                       Boolean justificatifRequis, Boolean absenceRemuneree,
                       Boolean impactSoldeConge, Boolean essMss,
                       Date dateDebut, Date dateFin) {
        this();
        this.idTypeAbsence = idTypeAbsence;
        this.nomAbsence = nomAbsence;
        this.codeAbsence = codeAbsence;
        this.justificatifRequis = justificatifRequis;
        this.absenceRemuneree = absenceRemuneree;
        this.impactSoldeConge = impactSoldeConge;
        this.essMss = essMss;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
}
