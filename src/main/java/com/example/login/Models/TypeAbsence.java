package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "decompte")
    private String decompte;  // ✅ NOUVEAU CHAMP : "Jours", "Heures", "Demi-journées", etc.

    @Column(name = "justificatif_requis")
    private Boolean justificatifRequis;

    @Column(name = "absence_remuneree")
    private Boolean absenceRemuneree;

    @Column(name = "impact_solde_conge")
    private Boolean impactSoldeConge;

    @Column(name = "ess_mss")
    private Boolean essMss;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    // ✅ CORRECTION : CascadeType.ALL → PERSIST, MERGE + @JsonIgnore pour éviter boucle infinie
    @OneToMany(mappedBy = "typeAbsence", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Absence> absences = new HashSet<>();

    // Default constructor
    public TypeAbsence() {
        this.justificatifRequis = false;
        this.absenceRemuneree = false;
        this.impactSoldeConge = false;
        this.essMss = false;
    }

    // Constructor with essential fields
    public TypeAbsence(String idTypeAbsence, String nomAbsence, String codeAbsence, String decompte) {
        this();
        this.idTypeAbsence = idTypeAbsence;
        this.nomAbsence = nomAbsence;
        this.codeAbsence = codeAbsence;
        this.decompte = decompte;
    }

    // Constructor with all fields
    public TypeAbsence(String idTypeAbsence, String nomAbsence, String codeAbsence, String decompte,
                       Boolean justificatifRequis, Boolean absenceRemuneree,
                       Boolean impactSoldeConge, Boolean essMss,
                       Date dateDebut, Date dateFin) {
        this();
        this.idTypeAbsence = idTypeAbsence;
        this.nomAbsence = nomAbsence;
        this.codeAbsence = codeAbsence;
        this.decompte = decompte;
        this.justificatifRequis = justificatifRequis;
        this.absenceRemuneree = absenceRemuneree;
        this.impactSoldeConge = impactSoldeConge;
        this.essMss = essMss;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public String getId() {
        return this.idTypeAbsence;
    }

    @Override
    public String toString() {
        return "TypeAbsence{" +
                "idTypeAbsence='" + idTypeAbsence + '\'' +
                ", nomAbsence='" + nomAbsence + '\'' +
                ", codeAbsence='" + codeAbsence + '\'' +
                ", decompte='" + decompte + '\'' +
                ", justificatifRequis=" + justificatifRequis +
                ", absenceRemuneree=" + absenceRemuneree +
                ", impactSoldeConge=" + impactSoldeConge +
                ", essMss=" + essMss +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeAbsence)) return false;
        TypeAbsence that = (TypeAbsence) o;
        return idTypeAbsence != null && idTypeAbsence.equals(that.idTypeAbsence);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}