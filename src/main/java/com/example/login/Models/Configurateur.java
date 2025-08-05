package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonBackReference // Côté "enfant" pour casser la boucle JSON
    private Utilisateur utilisateur;

    @Column(name = "region", nullable = false)
    private String region;

    // Note : Le @JsonIgnoreProperties n'est plus nécessaire ici car @JsonBackReference est plus fort.
    // Mais le laisser ne pose pas de problème.

    public String getIdConfigurateur() {
        return this.id != null ? this.id.toString() : null;
    }
}