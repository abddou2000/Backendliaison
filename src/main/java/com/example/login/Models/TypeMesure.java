package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "type_mesure")
@Getter
@Setter
public class TypeMesure {
    
    @Id
    @Column(name = "id_typemesure")
    private String idTypeMesure;
    
    @Column(name = "code")
    private String code;
    
    @Column(name = "nom")
    private String nom;
    
    @Column(name = "embauche")
    private Boolean embauche;
    
    @OneToMany(mappedBy = "typeMesure")
    private List<Mesure> mesures;
    
    // Default constructor
    public TypeMesure() {
    }
    
    // Constructor with essential fields
    public TypeMesure(String idTypeMesure, String code, String nom, Boolean embauche) {
        this.idTypeMesure = idTypeMesure;
        this.code = code;
        this.nom = nom;
        this.embauche = embauche;
    }
    
    // Create a new measure type
    public static TypeMesure createTypeMesure(TypeMesure data) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return data;
    }
    
    // Update existing measure type
    public static TypeMesure updateTypeMesure(String id, TypeMesure data) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        data.setIdTypeMesure(id);
        return data;
    }
    
    // Delete a measure type
    public static boolean deleteTypeMesure(String id) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return false;
    }
    
    // Get a measure type by ID
    public static TypeMesure getTypeMesureById(String id) {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return null;
    }
    
    // List all measure types
    public static List<TypeMesure> listTypesMesure() {
        // Note: This would typically be implemented in a repository or service class
        // This is just a placeholder signature
        return null;
    }
    
    @Override
    public String toString() {
        return "TypeMesure{" +
                "idTypeMesure='" + idTypeMesure + '\'' +
                ", code='" + code + '\'' +
                ", nom='" + nom + '\'' +
                ", embauche=" + embauche +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeMesure)) return false;
        TypeMesure that = (TypeMesure) o;
        return idTypeMesure != null && idTypeMesure.equals(that.idTypeMesure);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}