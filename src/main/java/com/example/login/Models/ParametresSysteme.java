package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "parametres_systeme")
@Getter
@Setter
public class ParametresSysteme {
    
    @Id
    @Column(name = "id_parametre")
    private String idParametre;
    
    @ManyToOne
    @JoinColumn(name = "id_configurateur")
    private Configurateur configurateur;
    
    @Column(name = "fuseau_horaire")
    private String fuseauHoraire;
    
    @Column(name = "format_date")
    private String formatDate;
    
    @Column(name = "format_heure")
    private String formatHeure;
    
    @Column(name = "email_systeme")
    private String emailSysteme;
    
    @Column(name = "notifications_actives")
    private Boolean notificationsActives;
    
    @Column(name = "date_modification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModification;
    
    // Default constructor
    public ParametresSysteme() {
    }
    
    // Constructor with essential fields
    public ParametresSysteme(String idParametre, Configurateur configurateur, 
                            String fuseauHoraire, String formatDate, String formatHeure) {
        this.idParametre = idParametre;
        this.configurateur = configurateur;
        this.fuseauHoraire = fuseauHoraire;
        this.formatDate = formatDate;
        this.formatHeure = formatHeure;
        this.dateModification = new Date();
    }
    
    // Constructor with all fields
    public ParametresSysteme(String idParametre, Configurateur configurateur, 
                            String fuseauHoraire, String formatDate, String formatHeure,
                            String emailSysteme, Boolean notificationsActives, Date dateModification) {
        this.idParametre = idParametre;
        this.configurateur = configurateur;
        this.fuseauHoraire = fuseauHoraire;
        this.formatDate = formatDate;
        this.formatHeure = formatHeure;
        this.emailSysteme = emailSysteme;
        this.notificationsActives = notificationsActives;
        this.dateModification = dateModification;
    }
    
    // Method to create new system parameters
    public static ParametresSysteme createParametresSysteme(ParametresSysteme data) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        data.setDateModification(new Date());
        return data;
    }
    
    // Method to update system parameters
    public static ParametresSysteme updateParametresSysteme(String id, ParametresSysteme data) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        data.setIdParametre(id);
        data.setDateModification(new Date());
        return data;
    }
    
    // Method to get system parameters
    public static ParametresSysteme getParametresSysteme() {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return null;
    }
    
    @Override
    public String toString() {
        return "ParametresSysteme{" +
                "idParametre='" + idParametre + '\'' +
                ", configurateur=" + (configurateur != null ? configurateur.getIdConfigurateur() : "null") +
                ", fuseauHoraire='" + fuseauHoraire + '\'' +
                ", formatDate='" + formatDate + '\'' +
                ", formatHeure='" + formatHeure + '\'' +
                ", emailSysteme='" + emailSysteme + '\'' +
                ", notificationsActives=" + notificationsActives +
                ", dateModification=" + dateModification +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParametresSysteme)) return false;
        ParametresSysteme that = (ParametresSysteme) o;
        return idParametre != null && idParametre.equals(that.idParametre);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}