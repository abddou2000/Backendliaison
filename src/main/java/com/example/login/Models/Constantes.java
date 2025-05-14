package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "constantes")
@Getter
@Setter
public class Constantes {
    
    @Id
    @Column(name = "id_const")
    private String idConst;
    
    @Column(name = "code_const")
    private String codeConst;
    
    @Column(name = "nom_const")
    private String nomConst;
    
    @Column(name = "valeur")
    private String valeur;
    
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    
    @ManyToOne
    @JoinColumn(name = "id_configurateur")
    private Configurateur configurateur;
    
    // Default constructor
    public Constantes() {
    }
    
    // Constructor with essential fields
    public Constantes(String idConst, String codeConst, String nomConst, 
                     String valeur, Date dateDebut, Configurateur configurateur) {
        this.idConst = idConst;
        this.codeConst = codeConst;
        this.nomConst = nomConst;
        this.valeur = valeur;
        this.dateDebut = dateDebut;
        this.configurateur = configurateur;
    }
    
    // Constructor with all fields
    public Constantes(String idConst, String codeConst, String nomConst, 
                     String valeur, Date dateDebut, Date dateFin, Configurateur configurateur) {
        this.idConst = idConst;
        this.codeConst = codeConst;
        this.nomConst = nomConst;
        this.valeur = valeur;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.configurateur = configurateur;
    }
    
    // Create a new constant
    public static Constantes createConstante(Constantes data) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return data;
    }
    
    // Update existing constant
    public static Constantes updateConstante(String id, Constantes data) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        data.setIdConst(id);
        return data;
    }
    
    // Delete a constant
    public static boolean deleteConstante(String id) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return false;
    }
    
    // Get a constant by ID
    public static Constantes getConstanteById(String id) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return null;
    }
    
    // List all constants
    public static List<Constantes> listConstantes() {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return null;
    }
    
    @Override
    public String toString() {
        return "Constantes{" +
                "idConst='" + idConst + '\'' +
                ", codeConst='" + codeConst + '\'' +
                ", nomConst='" + nomConst + '\'' +
                ", valeur='" + valeur + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", configurateur=" + (configurateur != null ? configurateur.getIdConfigurateur() : "null") +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Constantes)) return false;
        Constantes that = (Constantes) o;
        return idConst != null && idConst.equals(that.idConst);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}