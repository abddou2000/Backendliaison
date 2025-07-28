package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "resultat_paie")
@Getter @Setter
public class ResultatPaie {

    @Id
    @Column(name = "id_resultat")
    private String idResultat;

    // référence à l’employé (EmployeSimple.idEmploye)
    @ManyToOne
    @JoinColumn(name = "matricule")
    private EmployeSimple employe;

    // référence à la période (PeriodePaie.idPeriode)
    @ManyToOne
    @JoinColumn(name = "periode_id")
    private PeriodePaie periodePaie;

    // référence à la société (Societe.idSociete ou ce qui est @Id dans Societe)
    @ManyToOne
    @JoinColumn(name = "societe_id")
    private Societe societe;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    // chaque ligne de bulletin (RubriqueBulletin.resultatPaie)
    @OneToMany(mappedBy = "resultatPaie", cascade = CascadeType.ALL)
    private List<RubriqueBulletin> rubriques;
}
