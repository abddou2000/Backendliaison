package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // ✅ évite les problèmes de proxy
public class FichePaie {

    @Id
    @Column(name = "id_fiche")
    private String idFiche;

    // ✅ On ignore l'employé côté JSON (sinon Jackson veut tout l'objet complet)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @JsonIgnore
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

    // ✅ Pareil pour la période de paie
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_periode")
    @JsonIgnore
    private PeriodePaie periodePaie;

    // ✅ Et les demandes de document, on ne les sérialise pas ici
    @OneToMany(mappedBy = "fichePaie", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<DemandeDocument> demandesDocuments = new HashSet<>();

    @Override
    public String toString() {
        return "FichePaie{" +
                "idFiche='"      + idFiche + '\'' +
                ", employe="     + (employe != null ? employe.getId() : "null") +
                ", periodeAnnee="+ periodeAnnee +
                ", periodeMois=" + periodeMois +
                ", dateGeneration="+ dateGeneration +
                '}';
    }
}
