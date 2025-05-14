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
    
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    
    @OneToMany(mappedBy = "typeAbsence", cascade = CascadeType.ALL)
    private Set<Absence> absences = new HashSet<>();
    
    // Default constructor
    public TypeAbsence() {
    }
    
    // Constructor with essential fields
    public TypeAbsence(String idTypeAbsence, String nomAbsence, String codeAbsence) {
        this.idTypeAbsence = idTypeAbsence;
        this.nomAbsence = nomAbsence;
        this.codeAbsence = codeAbsence;
        this.justificatifRequis = false;
        this.absenceRemuneree = false;
        this.impactSoldeConge = false;
    }
    
    // Constructor with all fields except collections
    public TypeAbsence(String idTypeAbsence, String nomAbsence, String codeAbsence,
                      Boolean justificatifRequis, Boolean absenceRemuneree, 
                      Boolean impactSoldeConge, Date dateDebut, Date dateFin) {
        this.idTypeAbsence = idTypeAbsence;
        this.nomAbsence = nomAbsence;
        this.codeAbsence = codeAbsence;
        this.justificatifRequis = justificatifRequis;
        this.absenceRemuneree = absenceRemuneree;
        this.impactSoldeConge = impactSoldeConge;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    
    // Helper method for bidirectional relationship management
    public void addAbsence(Absence absence) {
        absences.add(absence);
        absence.setTypeAbsence(this);
    }
    
    public void removeAbsence(Absence absence) {
        absences.remove(absence);
        absence.setTypeAbsence(null);
    }
    
    @Override
    public String toString() {
        return "TypeAbsence{" +
                "idTypeAbsence='" + idTypeAbsence + '\'' +
                ", nomAbsence='" + nomAbsence + '\'' +
                ", codeAbsence='" + codeAbsence + '\'' +
                ", justificatifRequis=" + justificatifRequis +
                ", absenceRemuneree=" + absenceRemuneree +
                ", impactSoldeConge=" + impactSoldeConge +
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