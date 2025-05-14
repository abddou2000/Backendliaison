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
    @JoinColumn(name = "id_employe", referencedColumnName = "idEmploye")
    private EmployeSimple employe;
    
    @ManyToOne
    @JoinColumn(name = "id_societe", referencedColumnName = "idSociete")
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
    
    // Default constructor
    public Contract() {
    }
    
    // Constructor with essential fields
    public Contract(String idContrat, EmployeSimple employe, Societe societe, 
                    Date dateEmbauche, String fonction) {
        this.idContrat = idContrat;
        this.employe = employe;
        this.societe = societe;
        this.dateEmbauche = dateEmbauche;
        this.fonction = fonction;
    }
    
    // Constructor with all fields
    public Contract(String idContrat, EmployeSimple employe, Societe societe,
                   CategorieSalariale categorieSalariale, StatutSalarial statutSalarial,
                   UniteOrganisationnelle uniteOrganisationnelle, Date dateEmbauche,
                   Date dateAnciennete, Date dateFinContrat, String idTypeContrat,
                   String fonction, String departement, String service, String typeProfil,
                   String numeroCIMR, String numeroMutuelle, String responsableHierarchique,
                   String modeTravail, String periodeEssai, String conditionsSpecifiques) {
        this.idContrat = idContrat;
        this.employe = employe;
        this.societe = societe;
        this.categorieSalariale = categorieSalariale;
        this.statutSalarial = statutSalarial;
        this.uniteOrganisationnelle = uniteOrganisationnelle;
        this.dateEmbauche = dateEmbauche;
        this.dateAnciennete = dateAnciennete;
        this.dateFinContrat = dateFinContrat;
        this.idTypeContrat = idTypeContrat;
        this.fonction = fonction;
        this.departement = departement;
        this.service = service;
        this.typeProfil = typeProfil;
        this.numeroCIMR = numeroCIMR;
        this.numeroMutuelle = numeroMutuelle;
        this.responsableHierarchique = responsableHierarchique;
        this.modeTravail = modeTravail;
        this.periodeEssai = periodeEssai;
        this.conditionsSpecifiques = conditionsSpecifiques;
    }
    
    @Override
    public String toString() {
        return "Contract{" +
                "idContrat='" + idContrat + '\'' +
                ", employe=" + (employe != null ? employe.getIdEmploye() : "null") +
                ", societe=" + (societe != null ? societe.getIdSociete() : "null") +
                ", dateEmbauche=" + dateEmbauche +
                ", fonction='" + fonction + '\'' +
                '}';
    }
}