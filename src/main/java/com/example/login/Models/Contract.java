package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "contrat")
public class Contract {

    @Id
    @Column(name = "id_contrat")
    private String idContrat;

    @ManyToOne
    @JoinColumn(
            name = "id_employe",
            referencedColumnName = "id_user"
    )
    private EmployeSimple employe;

    @ManyToOne
    @JoinColumn(
            name = "id_societe",           // colonne FK dans la table contrat
            referencedColumnName = "idSociete" // colonne PK dans la table societe
    )
    private Societe societe;

    @ManyToOne
    @JoinColumn(name = "id_categoriesalariale")
    private CategorieSalariale categorieSalariale;

    @ManyToOne
    @JoinColumn(name = "id_statutsalarial")
    private StatutSalarial statutSalarial;

    @ManyToOne
    @JoinColumn(name = "id_uniteorganisationnelle")
    private UniteOrganisationnelle uniteOrganisationnelle;

    @Column(name = "date_embauche")
    @Temporal(TemporalType.DATE)
    private Date dateEmbauche;

    @Column(name = "date_anciennete")
    @Temporal(TemporalType.DATE)
    private Date dateAnciennete;

    @Column(name = "date_fin_contrat")
    @Temporal(TemporalType.DATE)
    private Date dateFinContrat;

    @Column(name = "id_typecontrat")
    private String idTypeContrat;

    private String fonction;
    private String departement;
    private String service;

    @Column(name = "type_profil")
    private String typeProfil;

    @Column(name = "numero_CIMR")
    private String numeroCIMR;

    @Column(name = "numero_mutuelle")
    private String numeroMutuelle;

    @Column(name = "responsable_hierarchique")
    private String responsableHierarchique;

    @Column(name = "mode_travail")
    private String modeTravail;

    @Column(name = "periode_essai")
    private String periodeEssai;

    @Column(name = "conditions_specifiques")
    private String conditionsSpecifiques;

    @Override
    public String toString() {
        return "Contract{" +
                "idContrat='" + idContrat + '\'' +
                ", employe=" + (employe != null ? employe.getId() : "null") +
                ", societe="  + (societe  != null ? societe.getId()  : "null") +
                ", dateEmbauche=" + dateEmbauche +
                ", fonction='"    + fonction + '\'' +
                '}';
    }
}
