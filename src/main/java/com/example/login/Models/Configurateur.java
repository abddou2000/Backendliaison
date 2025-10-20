package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "configurateur")
@Getter
@Setter
@NoArgsConstructor
public class Configurateur {

    @Id
    @Column(name = "id_user")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id_user")
    @JsonBackReference
    private Utilisateur utilisateur;

    @Column(name = "region", nullable = false)
    private String region;

    // ✅ Méthode utilitaire pour obtenir le matricule via l'utilisateur
    public String getMatricule() {
        return utilisateur != null ? utilisateur.getMatricule() : null;
    }

    public String getIdConfigurateur() {
        return this.id != null ? this.id.toString() : null;
    }
}