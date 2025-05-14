package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "type_motif_mesure")
@Getter
@Setter
public class TypeMotifMesure {
    
    @Id
    @Column(name = "id_typemotifmesure")
    private String idTypeMotifMesure;
    
    @Column(name = "code")
    private String code;
    
    @Column(name = "libelle")
    private String libelle;
    
    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "typeMotifMesure")
    private List<MotifMesure> motifMesures;
    
    // Default constructor
    public TypeMotifMesure() {
    }
    
    // Constructor with essential fields
    public TypeMotifMesure(String idTypeMotifMesure, String code, String libelle) {
        this.idTypeMotifMesure = idTypeMotifMesure;
        this.code = code;
        this.libelle = libelle;
    }
    
    // Constructor with all fields
    public TypeMotifMesure(String idTypeMotifMesure, String code, String libelle, String description) {
        this.idTypeMotifMesure = idTypeMotifMesure;
        this.code = code;
        this.libelle = libelle;
        this.description = description;
    }
    
    // Create a new type of motif mesure
    public static TypeMotifMesure createTypeMotifMesure(TypeMotifMesure data) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return data;
    }
    
    // Update existing type of motif mesure
    public static TypeMotifMesure updateTypeMotifMesure(String id, TypeMotifMesure data) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        data.setIdTypeMotifMesure(id);
        return data;
    }
    
    // Delete a type of motif mesure
    public static boolean deleteTypeMotifMesure(String id) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return false;
    }
    
    // Get a type of motif mesure by ID
    public static TypeMotifMesure getTypeMotifMesureById(String id) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return null;
    }
    
    // List all types of motif mesure
    public static List<TypeMotifMesure> listTypesMotifMesure() {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return null;
    }
    
    @Override
    public String toString() {
        return "TypeMotifMesure{" +
                "idTypeMotifMesure='" + idTypeMotifMesure + '\'' +
                ", code='" + code + '\'' +
                ", libelle='" + libelle + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeMotifMesure)) return false;
        TypeMotifMesure that = (TypeMotifMesure) o;
        return idTypeMotifMesure != null && idTypeMotifMesure.equals(that.idTypeMotifMesure);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}