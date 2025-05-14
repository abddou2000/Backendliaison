package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sauvegarde_bdd")
@Getter
@Setter
public class SauvegardeBDD {
    
    @Id
    @Column(name = "id_sauvegarde")
    private String idSauvegarde;
    
    @Column(name = "nom_fichier")
    private String nomFichier;
    
    @Column(name = "emplacement")
    private String emplacement;
    
    @Column(name = "date_sauvegarde")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSauvegarde;
    
    @ManyToOne
    @JoinColumn(name = "cree_par")
    private Administrateur creePar;
    
    // Default constructor
    public SauvegardeBDD() {
    }
    
    // Constructor with essential fields
    public SauvegardeBDD(String idSauvegarde, String nomFichier, String emplacement, 
                        Administrateur creePar) {
        this.idSauvegarde = idSauvegarde;
        this.nomFichier = nomFichier;
        this.emplacement = emplacement;
        this.dateSauvegarde = new Date();
        this.creePar = creePar;
    }
    
    // Constructor with all fields
    public SauvegardeBDD(String idSauvegarde, String nomFichier, String emplacement, 
                        Date dateSauvegarde, Administrateur creePar) {
        this.idSauvegarde = idSauvegarde;
        this.nomFichier = nomFichier;
        this.emplacement = emplacement;
        this.dateSauvegarde = dateSauvegarde;
        this.creePar = creePar;
    }
    
    // Create new database backup record
    public static SauvegardeBDD createSauvegardeBDD(SauvegardeBDD data) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        if (data.getDateSauvegarde() == null) {
            data.setDateSauvegarde(new Date());
        }
        return data;
    }
    
    // Delete database backup record
    public static boolean deleteSauvegardeBDD(String id) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return false;
    }
    
    // List all database backups
    public static List<SauvegardeBDD> listSauvegardes() {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return null;
    }
    
    @Override
    public String toString() {
        return "SauvegardeBDD{" +
                "idSauvegarde='" + idSauvegarde + '\'' +
                ", nomFichier='" + nomFichier + '\'' +
                ", emplacement='" + emplacement + '\'' +
                ", dateSauvegarde=" + dateSauvegarde +
                ", creePar=" + (creePar != null ? creePar.getIdAdministrateur() : "null") +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SauvegardeBDD)) return false;
        SauvegardeBDD that = (SauvegardeBDD) o;
        return idSauvegarde != null && idSauvegarde.equals(that.idSauvegarde);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}