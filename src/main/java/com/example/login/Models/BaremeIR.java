package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "bareme_ir")
@Getter
@Setter
public class BaremeIR {

    @Id
    @Column(name = "id_tranche")
    private String idTranche;

    @Column(name = "nom_tranche")
    private String nomTranche;  // ✅ NOUVEAU CHAMP : "Tranche 1 - 0 à 30 000 DH"

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

    // Default constructor
    public BaremeIR() {}

    // Constructor with essential fields
    public BaremeIR(String idTranche, String nomTranche, Double minimum, Double maximum,
                    Double tauxIr, Double montantDeduction) {
        this.idTranche = idTranche;
        this.nomTranche = nomTranche;
        this.minimum = minimum;
        this.maximum = maximum;
        this.tauxIr = tauxIr;
        this.montantDeduction = montantDeduction;
    }

    // Constructor with all fields
    public BaremeIR(String idTranche, String nomTranche, Double minimum, Double maximum,
                    Double tauxIr, Double montantDeduction, Date dateDebut, Date dateFin) {
        this.idTranche = idTranche;
        this.nomTranche = nomTranche;
        this.minimum = minimum;
        this.maximum = maximum;
        this.tauxIr = tauxIr;
        this.montantDeduction = montantDeduction;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "BaremeIR{" +
                "idTranche='" + idTranche + '\'' +
                ", nomTranche='" + nomTranche + '\'' +
                ", minimum=" + minimum +
                ", maximum=" + maximum +
                ", tauxIr=" + tauxIr +
                ", montantDeduction=" + montantDeduction +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
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