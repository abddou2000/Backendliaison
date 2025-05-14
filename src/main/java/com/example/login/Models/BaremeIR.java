package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bareme_ir")
@Getter
@Setter
public class BaremeIR {
    
    @Id
    @Column(name = "id_tranche")
    private String idTranche;
    
    @Column(name = "minimum")
    private Double minimum;
    
    @Column(name = "maximum")
    private Double maximum;
    
    @Column(name = "taux_ir")
    private Double tauxIr;
    
    @Column(name = "montant_deduction")
    private Double montantDeduction;
    
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    
    @ManyToOne
    @JoinColumn(name = "id_configurateur")
    private Configurateur configurateur;
    
    // Default constructor
    public BaremeIR() {
    }
    
    // Constructor with essential fields
    public BaremeIR(String idTranche, Double minimum, Double maximum, Double tauxIr, 
                  Double montantDeduction, Date dateDebut, Configurateur configurateur) {
        this.idTranche = idTranche;
        this.minimum = minimum;
        this.maximum = maximum;
        this.tauxIr = tauxIr;
        this.montantDeduction = montantDeduction;
        this.dateDebut = dateDebut;
        this.configurateur = configurateur;
    }
    
    // Constructor with all fields
    public BaremeIR(String idTranche, Double minimum, Double maximum, Double tauxIr, 
                  Double montantDeduction, Date dateDebut, Date dateFin, Configurateur configurateur) {
        this.idTranche = idTranche;
        this.minimum = minimum;
        this.maximum = maximum;
        this.tauxIr = tauxIr;
        this.montantDeduction = montantDeduction;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.configurateur = configurateur;
    }
    
    // Create a new IR tax bracket
    public static BaremeIR createBaremeIR(BaremeIR data) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return data;
    }
    
    // Update existing IR tax bracket
    public static BaremeIR updateBaremeIR(String id, BaremeIR data) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        data.setIdTranche(id);
        return data;
    }
    
    // Delete an IR tax bracket
    public static boolean deleteBaremeIR(String id) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return false;
    }
    
    // Get an IR tax bracket by ID
    public static BaremeIR getBaremeIRById(String id) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return null;
    }
    
    // List all IR tax brackets
    public static List<BaremeIR> listBaremeIR() {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return null;
    }
    
    @Override
    public String toString() {
        return "BaremeIR{" +
                "idTranche='" + idTranche + '\'' +
                ", minimum=" + minimum +
                ", maximum=" + maximum +
                ", tauxIr=" + tauxIr +
                ", montantDeduction=" + montantDeduction +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", configurateur=" + (configurateur != null ? configurateur.getIdConfigurateur() : "null") +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaremeIR)) return false;
        BaremeIR that = (BaremeIR) o;
        return idTranche != null && idTranche.equals(that.idTranche);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}