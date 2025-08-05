package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rh")
@Getter
@Setter
@NoArgsConstructor
public class Rh {
    @Id
    @Column(name = "id_user")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id_user")
    @JsonBackReference // Côté "enfant" pour casser la boucle JSON
    private Utilisateur utilisateur;

    @Column(name = "services")
    private String services;
}