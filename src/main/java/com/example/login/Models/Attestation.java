package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "attestation")
@Getter
@Setter
public class Attestation {
    
    @Id
    @Column(name = "id_attestation")
    private String idAttestation;
    
    @ManyToOne
    @JoinColumn(name = "id_type_attestation")
    private TypeAttestation typeAttestation;
    
    @Column(name = "nom_attestation")
    private String nomAttestation;
    
    @OneToMany(mappedBy = "attestation", cascade = CascadeType.ALL)
    private Set<DemandeDocument> demandesDocuments = new HashSet<>();
    
    // Default constructor
    public Attestation() {
    }
    
    // Constructor with essential fields
    public Attestation(String idAttestation, String nomAttestation) {
        this.idAttestation = idAttestation;
        this.nomAttestation = nomAttestation;
    }
    
    // Constructor with all fields except collections
    public Attestation(String idAttestation, TypeAttestation typeAttestation, String nomAttestation) {
        this.idAttestation = idAttestation;
        this.typeAttestation = typeAttestation;
        this.nomAttestation = nomAttestation;
    }
    
    // Helper methods for bidirectional relationship management
    public void addDemandeDocument(DemandeDocument demandeDocument) {
        demandesDocuments.add(demandeDocument);
        demandeDocument.setAttestation(this);
    }
    
    public void removeDemandeDocument(DemandeDocument demandeDocument) {
        demandesDocuments.remove(demandeDocument);
        demandeDocument.setAttestation(null);
    }
    
    @Override
    public String toString() {
        return "Attestation{" +
                "idAttestation='" + idAttestation + '\'' +
                ", typeAttestation=" + (typeAttestation != null ? typeAttestation.getIdTypeAttestation() : "null") +
                ", nomAttestation='" + nomAttestation + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attestation)) return false;
        Attestation that = (Attestation) o;
        return idAttestation != null && idAttestation.equals(that.idAttestation);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}