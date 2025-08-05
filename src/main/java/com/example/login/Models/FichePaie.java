package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fiche_paie")
@Getter
@Setter
public class FichePaie {

    @Id
    @Column(name = "id_fiche")
    private String idFiche;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private EmployeSimple employe;

    @Column(name = "periode_annee")
    private Integer periodeAnnee;

    @Column(name = "periode_mois")
    private Integer periodeMois;

    @Column(name = "lien_pdf")
    private String lienPdf;

    @Column(name = "date_generation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateGeneration;

    @ManyToOne
    @JoinColumn(name = "id_periode")
    private PeriodePaie periodePaie;

    @OneToMany(mappedBy = "fichePaie", cascade = CascadeType.ALL)
    private Set<DemandeDocument> demandesDocuments = new HashSet<>();

    @Override
    public String toString() {
        return "FichePaie{" +
                "idFiche='"     + idFiche + '\'' +
                ", employe="    + (employe    != null ? employe.getId() : "null") +
                ", periodeAnnee="+ periodeAnnee +
                ", periodeMois="+  periodeMois +
                ", dateGeneration="+ dateGeneration +
                '}';
    }
}
