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
    @JoinColumn(
            name = "id_employe",           // colonne FK dans solde_conges
            referencedColumnName = "id_user" // PK de employe_simple
    )
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

    public SoldeConges() {}
    // autres constructeurs et méthodes inchangés
}
