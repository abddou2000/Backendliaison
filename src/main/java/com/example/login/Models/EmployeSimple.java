package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "employe_simple")
@Getter
@Setter
@NoArgsConstructor
public class EmployeSimple {
    @Id
    @Column(name = "id_user")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id_user")
    @JsonBackReference // Côté "enfant" pour casser la boucle JSON
    private Utilisateur utilisateur;

    @Column(name = "cin", unique = true, nullable = false)
    private String cin;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "lieu_naissance")
    private String lieuNaissance;

    @Column(name = "genre")
    private String genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unite_organisationnelle_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UniteOrganisationnelle uniteOrganisationnelle;

    public Long getIdEmploye() {
        return this.id;
    }

    public void setIdEmploye(Long idEmploye) {
        this.id = idEmploye;
    }

    public Long getIdUtilisateur() {
        return this.utilisateur != null ? this.utilisateur.getId() : null;
    }
}