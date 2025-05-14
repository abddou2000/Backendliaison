package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "solde_conges")
@Getter
@Setter
public class SoldeConges {
    
    @Id
    @Column(name = "id_solde")
    private String idSolde;
    
    @ManyToOne
    @JoinColumn(name = "id_employe")
    private EmployeSimple employe;
    
    @Column(name = "solde_total")
    private Integer soldeTotal;
    
    @Column(name = "solde_pris")
    private Integer soldePris;
    
    @Column(name = "solde_restant")
    private Integer soldeRestant;
    
    @Column(name = "annee")
    private Integer annee;
    
    @Column(name = "mois_reporte")
    private Integer moisReporte;
    
    // Default constructor
    public SoldeConges() {
    }
    
    // Constructor with essential fields
    public SoldeConges(String idSolde, EmployeSimple employe, Integer soldeTotal, Integer annee) {
        this.idSolde = idSolde;
        this.employe = employe;
        this.soldeTotal = soldeTotal;
        this.soldePris = 0;
        this.soldeRestant = soldeTotal;
        this.annee = annee;
    }
    
    // Constructor with all fields
    public SoldeConges(String idSolde, EmployeSimple employe, Integer soldeTotal,
                      Integer soldePris, Integer soldeRestant, Integer annee, Integer moisReporte) {
        this.idSolde = idSolde;
        this.employe = employe;
        this.soldeTotal = soldeTotal;
        this.soldePris = soldePris;
        this.soldeRestant = soldeRestant;
        this.annee = annee;
        this.moisReporte = moisReporte;
    }
    
    // Method to update vacation balance
    public void updateSoldeConges(String employeId, Integer newSolde) {
        if (this.employe != null && this.employe.getIdEmploye().equals(employeId)) {
            this.soldeRestant = newSolde;
            this.soldePris = this.soldeTotal - this.soldeRestant;
        }
    }
    
    // Calculate new vacation balance considering absences
    public Integer getNewSoldeConges(String employeId, Integer nombreAbsences) {
        if (this.employe != null && this.employe.getIdEmploye().equals(employeId)) {
            return this.soldeRestant - nombreAbsences;
        }
        return null;
    }
    
    // Get vacation balance for a specific employee
    public static SoldeConges getSoldeCongesByEmploye(String employeId) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return null;
    }
    
    @Override
    public String toString() {
        return "SoldeConges{" +
                "idSolde='" + idSolde + '\'' +
                ", employe=" + (employe != null ? employe.getIdEmploye() : "null") +
                ", soldeTotal=" + soldeTotal +
                ", soldePris=" + soldePris +
                ", soldeRestant=" + soldeRestant +
                ", annee=" + annee +
                ", moisReporte=" + moisReporte +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SoldeConges)) return false;
        SoldeConges that = (SoldeConges) o;
        return idSolde != null && idSolde.equals(that.idSolde);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}