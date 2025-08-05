package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "remuneration")
@Getter
@Setter
public class Remuneration {

    @Id
    @Column(name = "id_remuneration")
    private String idRemuneration;

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", unique = true)
    private EmployeSimple employe;

    @Column(name = "rib")
    private String rib;

    @Column(name = "bic")
    private String bic;

    @Column(name = "banque")
    private String banque;

    @Column(name = "salaire_base", precision = 10, scale = 2)
    private BigDecimal salaireBase;

    @Column(name = "taux_activite")
    private Double tauxActivite;

    @Column(name = "mode_paiement")
    private String modePaiement;

    // Default constructor
    public Remuneration() {}

    public Remuneration(String idRemuneration, EmployeSimple employe, String rib, String bic, String banque,
                        BigDecimal salaireBase, Double tauxActivite, String modePaiement) {
        this.idRemuneration = idRemuneration;
        this.employe = employe;
        this.rib = rib;
        this.bic = bic;
        this.banque = banque;
        this.salaireBase = salaireBase;
        this.tauxActivite = tauxActivite;
        this.modePaiement = modePaiement;
    }

    // Create a remuneration record
    public static Remuneration createRemuneration(Remuneration data) {
        // Typically handled in a service/repository, placeholder here
        return data;
    }

    // Update remuneration information
    public static Remuneration updateRemuneration(String id, Remuneration data) {
        // Typically handled in a service/repository, placeholder here
        data.setIdRemuneration(id);
        return data;
    }

    // Get remuneration by employee
    public static Remuneration getRemunerationByEmploye(String employeId) {
        // Typically handled in a repository/service, placeholder here
        return null;
    }
}