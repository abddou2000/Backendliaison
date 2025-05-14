package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "jour_ferie")
@Getter
@Setter
public class JourFerie {
    
    @Id
    @Column(name = "id_jour_ferie")
    private String idJourFerie;
    
    @ManyToOne
    @JoinColumn(name = "id_parametreur")
    private Configurateur parametreur;
    
    @Column(name = "nom_jour")
    private String nomJour;
    
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    
    @Column(name = "recurrence_annuelle")
    private Boolean recurrenceAnnuelle;
    
    // Default constructor
    public JourFerie() {
    }
    
    // Constructor with essential fields
    public JourFerie(String idJourFerie, Configurateur parametreur, String nomJour, 
                    Date dateDebut, Boolean recurrenceAnnuelle) {
        this.idJourFerie = idJourFerie;
        this.parametreur = parametreur;
        this.nomJour = nomJour;
        this.dateDebut = dateDebut;
        this.recurrenceAnnuelle = recurrenceAnnuelle;
    }
    
    // Constructor with all fields
    public JourFerie(String idJourFerie, Configurateur parametreur, String nomJour,
                    Date dateDebut, Date dateFin, Boolean recurrenceAnnuelle) {
        this.idJourFerie = idJourFerie;
        this.parametreur = parametreur;
        this.nomJour = nomJour;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.recurrenceAnnuelle = recurrenceAnnuelle;
    }
    
    // Create a new holiday
    public static JourFerie createJourFerie(JourFerie data) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return data;
    }
    
    // Update existing holiday

    
    // List all holidays
    public static List<JourFerie> listJoursFeries() {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return null;
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JourFerie)) return false;
        JourFerie that = (JourFerie) o;
        return idJourFerie != null && idJourFerie.equals(that.idJourFerie);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}