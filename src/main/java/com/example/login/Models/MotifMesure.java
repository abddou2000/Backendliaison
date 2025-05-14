package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "motif_mesure")
@Getter
@Setter
public class MotifMesure {
    
    @Id
    @Column(name = "id_motif")
    private String idMotif;
    
    @ManyToOne
    @JoinColumn(name = "id_mesure")
    private Mesure mesure;
    
    @Column(name = "motif")
    private String motif;
    
    @ManyToOne
    @JoinColumn(name = "id_typemotifmesure")
    private TypeMotifMesure typeMotifMesure;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    
    // Default constructor
    public MotifMesure() {
    }
    
    // Constructor with essential fields
    public MotifMesure(String idMotif, Mesure mesure, String motif, 
                      TypeMotifMesure typeMotifMesure, Date dateDebut) {
        this.idMotif = idMotif;
        this.mesure = mesure;
        this.motif = motif;
        this.typeMotifMesure = typeMotifMesure;
        this.dateDebut = dateDebut;
    }
    
    // Constructor with all fields
    public MotifMesure(String idMotif, Mesure mesure, String motif, 
                      TypeMotifMesure typeMotifMesure, String description,
                      Date dateDebut, Date dateFin) {
        this.idMotif = idMotif;
        this.mesure = mesure;
        this.motif = motif;
        this.typeMotifMesure = typeMotifMesure;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    
    // Create a new motif mesure
    public static MotifMesure createMotifMesure(MotifMesure data) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return data;
    }
    
    // Update existing motif mesure
    public static MotifMesure updateMotifMesure(String id, MotifMesure data) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        data.setIdMotif(id);
        return data;
    }
    
    // Delete a motif mesure
    public static boolean deleteMotifMesure(String id) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return false;
    }
    
    // Get a motif mesure by ID
    public static MotifMesure getMotifMesureById(String id) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return null;
    }
    
    // List motif mesures by mesure
    public static List<MotifMesure> listMotifMesureByMesure(String idMesure) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return null;
    }
    
    @Override
    public String toString() {
        return "MotifMesure{" +
                "idMotif='" + idMotif + '\'' +
                ", mesure=" + (mesure != null ? mesure.getIdMesure() : "null") +
                ", motif='" + motif + '\'' +
                ", typeMotifMesure=" + (typeMotifMesure != null ? typeMotifMesure.getIdTypeMotifMesure() : "null") +
                ", description='" + description + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MotifMesure)) return false;
        MotifMesure that = (MotifMesure) o;
        return idMotif != null && idMotif.equals(that.idMotif);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}