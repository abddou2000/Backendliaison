package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "absence")
@Getter
@Setter
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
    
    // Default constructor
    public Absence() {
    }
    
    // Constructor with essential fields
    public Absence(String idAbsence, EmployeSimple employe, TypeAbsence typeAbsence, 
                  Date dateDebut, Date dateFin) {
        this.idAbsence = idAbsence;
        this.employe = employe;
        this.typeAbsence = typeAbsence;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    
    // Constructor with all fields
    public Absence(String idAbsence, EmployeSimple employe, TypeAbsence typeAbsence, 
                  PeriodePaie periodePaie, Date dateDebut, Date dateFin) {
        this.idAbsence = idAbsence;
        this.employe = employe;
        this.typeAbsence = typeAbsence;
        this.periodePaie = periodePaie;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    
    @Override
    public String toString() {
        return "Absence{" +
                "idAbsence='" + idAbsence + '\'' +
                ", employe=" + (employe != null ? employe.getIdEmploye() : "null") +
                ", typeAbsence=" + (typeAbsence != null ? typeAbsence.getIdTypeAbsence() : "null") +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Absence)) return false;
        Absence absence = (Absence) o;
        return idAbsence != null && idAbsence.equals(absence.idAbsence);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}