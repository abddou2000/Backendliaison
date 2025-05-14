package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "demande_document")
@Getter
@Setter
public class DemandeDocument {
    
    @Id
    @Column(name = "id_demande")
    private String idDemande;
    
    @ManyToOne
    @JoinColumn(name = "id_employe")
    private EmployeSimple employe;
    
    @ManyToOne
    @JoinColumn(name = "id_attestation")
    private Attestation attestation;
    
    @ManyToOne
    @JoinColumn(name = "id_fiche")
    private FichePaie fichePaie;
    
    @Column(name = "type_document")
    private String typeDocument;
    
    @Column(name = "motif")
    private String motif;
    
    @Column(name = "format_document")
    private String formatDocument; // PDF, Impression Papier
    
    @Column(name = "etat_demande")
    private String etatDemande; // en attente, en cours, validée, refusée
    
    @Column(name = "motif_refus")
    private String motifRefus;
    
    @Column(name = "date_demande")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDemande;
    
    @Column(name = "date_traitement")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTraitement;
    
    @Column(name = "lien_telechargement")
    private String lienTelechargement;
    
    // Default constructor
    public DemandeDocument() {
    }
    
    // Constructor with essential fields
    public DemandeDocument(String idDemande, EmployeSimple employe, String typeDocument) {
        this.idDemande = idDemande;
        this.employe = employe;
        this.typeDocument = typeDocument;
        this.etatDemande = "en attente";
        this.dateDemande = new Date(); // Current date/time
    }
    
    // Constructor with all fields except relationships
    public DemandeDocument(String idDemande, String typeDocument, String motif, 
                          String formatDocument, String etatDemande, String motifRefus,
                          Date dateDemande, Date dateTraitement, String lienTelechargement) {
        this.idDemande = idDemande;
        this.typeDocument = typeDocument;
        this.motif = motif;
        this.formatDocument = formatDocument;
        this.etatDemande = etatDemande;
        this.motifRefus = motifRefus;
        this.dateDemande = dateDemande;
        this.dateTraitement = dateTraitement;
        this.lienTelechargement = lienTelechargement;
    }
    
    // Full constructor with all fields
    public DemandeDocument(String idDemande, EmployeSimple employe, Attestation attestation,
                          FichePaie fichePaie, String typeDocument, String motif,
                          String formatDocument, String etatDemande, String motifRefus,
                          Date dateDemande, Date dateTraitement, String lienTelechargement) {
        this.idDemande = idDemande;
        this.employe = employe;
        this.attestation = attestation;
        this.fichePaie = fichePaie;
        this.typeDocument = typeDocument;
        this.motif = motif;
        this.formatDocument = formatDocument;
        this.etatDemande = etatDemande;
        this.motifRefus = motifRefus;
        this.dateDemande = dateDemande;
        this.dateTraitement = dateTraitement;
        this.lienTelechargement = lienTelechargement;
    }
    
    @Override
    public String toString() {
        return "DemandeDocument{" +
                "idDemande='" + idDemande + '\'' +
                ", employe=" + (employe != null ? employe.getIdEmploye() : "null") +
                ", typeDocument='" + typeDocument + '\'' +
                ", etatDemande='" + etatDemande + '\'' +
                ", dateDemande=" + dateDemande +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DemandeDocument)) return false;
        DemandeDocument that = (DemandeDocument) o;
        return idDemande != null && idDemande.equals(that.idDemande);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}