package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "type_attestation")
@Getter
@Setter
public class TypeAttestation {
    
    @Id
    @Column(name = "id_type_attestation")
    private String idTypeAttestation;
    
    @Column(name = "nom_type_attestation")
    private String nomTypeAttestation;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @OneToMany(mappedBy = "typeAttestation", cascade = CascadeType.ALL)
    private Set<Attestation> attestations = new HashSet<>();
    
    // Default constructor
    public TypeAttestation() {
    }
    
    // Constructor with essential fields
    public TypeAttestation(String idTypeAttestation, String nomTypeAttestation) {
        this.idTypeAttestation = idTypeAttestation;
        this.nomTypeAttestation = nomTypeAttestation;
    }
    
    // Constructor with all fields except collections
    public TypeAttestation(String idTypeAttestation, String nomTypeAttestation, String description) {
        this.idTypeAttestation = idTypeAttestation;
        this.nomTypeAttestation = nomTypeAttestation;
        this.description = description;
    }

    // Helper methods for bidirectional relationship management

    public void removeAttestation(Attestation attestation) {
        attestations.remove(attestation);
        attestation.setTypeAttestation(null);
    }
    
    @Override
    public String toString() {
        return "TypeAttestation{" +
                "idTypeAttestation='" + idTypeAttestation + '\'' +
                ", nomTypeAttestation='" + nomTypeAttestation + '\'' +
                ", description='" + (description != null ? description.substring(0, Math.min(description.length(), 30)) + "..." : "null") + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeAttestation)) return false;
        TypeAttestation that = (TypeAttestation) o;
        return idTypeAttestation != null && idTypeAttestation.equals(that.idTypeAttestation);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}