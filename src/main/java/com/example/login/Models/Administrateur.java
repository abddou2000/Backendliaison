package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "administrateur")
@Getter
@Setter
@NoArgsConstructor
public class Administrateur {
    @Id
    @Column(name = "id_user")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id_user")
    @JsonBackReference // Côté "enfant" pour casser la boucle JSON
    private Utilisateur utilisateur;

    @Column(name = "niveau")
    private String niveau;

    public String getIdAdministrateur() {
        return this.id != null ? this.id.toString() : null;
    }
}