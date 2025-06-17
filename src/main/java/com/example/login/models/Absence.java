package com.example.login.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "absence")
public class Absence {

    @Id
    @Column(name = "id_absence")
    private String idAbsence;

    @ManyToOne
    @JoinColumn(name = "id_employe")
    private EmployeSimple employe;

    @ManyToOne
    @JoinColumn(name = "id_type_absence")
    private TypeAbsence typeAbsence;

    @ManyToOne
    @JoinColumn(name = "id_periode")
    private PeriodePaie periodePaie;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @Column(name = "justificatif_requis")
    private boolean justificatifRequis;

    @Column(name = "absence_remuneree")
    private boolean absenceRemuneree;

    @Column(name = "code")
    private String code;

    @Column(name = "nom")
    private String nom;

    @Column(name = "impact_solde")
    private boolean impactSolde;

    public Absence() {
    }

    public Absence(String idAbsence,
                   EmployeSimple employe,
                   TypeAbsence typeAbsence,
                   PeriodePaie periodePaie,
                   Date dateDebut,
                   Date dateFin,
                   boolean justificatifRequis,
                   boolean absenceRemuneree,
                   String code,
                   String nom,
                   boolean impactSolde) {
        this.idAbsence = idAbsence;
        this.employe = employe;
        this.typeAbsence = typeAbsence;
        this.periodePaie = periodePaie;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.justificatifRequis = justificatifRequis;
        this.absenceRemuneree = absenceRemuneree;
        this.code = code;
        this.nom = nom;
        this.impactSolde = impactSolde;
    }

    @Override
    public String toString() {
        return "Absence{" +
                "idAbsence='" + idAbsence + '\'' +
                ", employe=" + (employe != null ? employe.getIdEmploye() : "null") +
                ", typeAbsence=" + (typeAbsence != null ? typeAbsence.getIdTypeAbsence() : "null") +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", justificatifRequis=" + justificatifRequis +
                ", absenceRemuneree=" + absenceRemuneree +
                ", code='" + code + '\'' +
                ", nom='" + nom + '\'' +
                ", impactSolde=" + impactSolde +
                '}';
    }
}
