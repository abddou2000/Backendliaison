package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "employes_simple")
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
    @JsonBackReference
    private Utilisateur utilisateur;

    // ✅ MATRICULE SUPPRIMÉ (il est maintenant dans Utilisateur)
    // @Column(name = "matricule", unique = true, nullable = false)
    // private String matricule;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "poste_occupe")
    private String posteOccupe;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;

    @Column(name = "situation_familiale")
    private String situationFamiliale;

    @Column(name = "enfants_a_charge")
    private Integer enfantsACharge;

    @Column(name = "departement")
    private String departement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unite_organisationnelle_id")
    private UniteOrganisationnelle uniteOrganisationnelle;

    @Column(name = "type_contrat")
    private String typeContrat;

    public enum Genre {
        H, F
    }

    // ✅ Méthode utilitaire pour obtenir le matricule via l'utilisateur
    public String getMatricule() {
        return utilisateur != null ? utilisateur.getMatricule() : null;
    }

    // Méthode utilitaire pour obtenir le nom complet
    public String getNomComplet() {
        if (utilisateur != null) {
            return utilisateur.getPrenom() + " " + utilisateur.getNom();
        }
        return "";
    }

    // Constructeur avec utilisateur
    public EmployeSimple(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.id = utilisateur.getId();
    }

    @Override
    public String toString() {
        return "EmployeSimple{" +
                "id=" + id +
                ", matricule='" + getMatricule() + '\'' +
                ", posteOccupe='" + posteOccupe + '\'' +
                ", departement='" + departement + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeSimple)) return false;
        EmployeSimple that = (EmployeSimple) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}