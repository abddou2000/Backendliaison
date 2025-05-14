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
    @JoinColumn(name = "id_employe")
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
    
    // Default constructor
    public FichePaie() {
    }
    
    // Constructor with essential fields
    public FichePaie(String idFiche, EmployeSimple employe, Integer periodeAnnee, 
                    Integer periodeMois) {
        this.idFiche = idFiche;
        this.employe = employe;
        this.periodeAnnee = periodeAnnee;
        this.periodeMois = periodeMois;
    }
    
    // Constructor with all fields except collections
    public FichePaie(String idFiche, EmployeSimple employe, Integer periodeAnnee,
                    Integer periodeMois, String lienPdf, Date dateGeneration,
                    PeriodePaie periodePaie) {
        this.idFiche = idFiche;
        this.employe = employe;
        this.periodeAnnee = periodeAnnee;
        this.periodeMois = periodeMois;
        this.lienPdf = lienPdf;
        this.dateGeneration = dateGeneration;
        this.periodePaie = periodePaie;
    }
    
    // Helper methods for bidirectional relationship management
    public void addDemandeDocument(DemandeDocument demandeDocument) {
        demandesDocuments.add(demandeDocument);
        demandeDocument.setFichePaie(this);
    }
    
    public void removeDemandeDocument(DemandeDocument demandeDocument) {
        demandesDocuments.remove(demandeDocument);
        demandeDocument.setFichePaie(null);
    }
    
    @Override
    public String toString() {
        return "FichePaie{" +
                "idFiche='" + idFiche + '\'' +
                ", employe=" + (employe != null ? employe.getIdEmploye() : "null") +
                ", periodeAnnee=" + periodeAnnee +
                ", periodeMois=" + periodeMois +
                ", dateGeneration=" + dateGeneration +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FichePaie)) return false;
        FichePaie fichePaie = (FichePaie) o;
        return idFiche != null && idFiche.equals(fichePaie.idFiche);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}