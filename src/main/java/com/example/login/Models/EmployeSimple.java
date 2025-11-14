package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnore
    private Utilisateur utilisateur;

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

    // ✅ Relation vers UniteOrganisationnelle (STRING)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unite")
    @JsonIgnore
    private UniteOrganisationnelle uniteOrganisationnelle;

    // ✅ Relation vers Societe (STRING)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_societe")
    @JsonIgnore
    private Societe societe;

    @Column(name = "type_contrat")
    private String typeContrat;

    @Column(name = "lieu_naissance")
    private String lieuNaissance;

    @Column(name = "nationalite")
    private String nationalite;

    @Column(name = "ville")
    private String ville;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "email_personnel")
    private String emailPersonnel;

    @Column(name = "numero_securite_sociale")
    private String numeroSecuriteSociale;

    @Column(name = "date_embauche")
    private LocalDate dateEmbauche;

    @Column(name = "manager")
    private String manager;

    @Column(name = "equipe")
    private String equipe;

    @Column(name = "telephone_pro")
    private String telephonePro;

    @Column(name = "formation")
    private String formation;

    @Column(name = "experience")
    private String experience;

    @Column(name = "photo_profil", length = 1000)
    private String photoProfil;

    @Column(name = "statut")
    private String statut;

    // DTO fields pour la sérialisation JSON
    @Transient
    @JsonProperty("utilisateurId")
    private Long utilisateurId;

    @Transient
    @JsonProperty("idUnite")
    private String idUniteCache;

    @Transient
    @JsonProperty("idSociete")
    private String idSocieteCache;

    @Transient
    @JsonProperty("matricule")
    private String matriculeCache;

    @Transient
    @JsonProperty("nomComplet")
    private String nomCompletCache;

    @Transient
    @JsonProperty("username")
    private String usernameCache;

    @Transient
    @JsonProperty("email")
    private String emailCache;

    public enum Genre {
        H, F
    }

    public EmployeSimple(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.id = utilisateur.getId();
        this.utilisateurId = utilisateur.getId();
    }

    // Getters pour les champs dérivés
    @JsonProperty("matricule")
    public String getMatricule() {
        if (matriculeCache != null) return matriculeCache;
        return utilisateur != null ? utilisateur.getMatricule() : null;
    }

    @JsonProperty("nomComplet")
    public String getNomComplet() {
        if (nomCompletCache != null) return nomCompletCache;
        if (utilisateur != null) {
            return utilisateur.getPrenom() + " " + utilisateur.getNom();
        }
        return "";
    }

    @JsonProperty("username")
    public String getUsername() {
        if (usernameCache != null) return usernameCache;
        return utilisateur != null ? utilisateur.getUsername() : null;
    }

    @JsonProperty("email")
    public String getEmail() {
        if (emailCache != null) return emailCache;
        return utilisateur != null ? utilisateur.getEmail() : null;
    }

    @JsonProperty("utilisateurId")
    public Long getUtilisateurId() {
        if (utilisateurId != null) return utilisateurId;
        return utilisateur != null ? utilisateur.getId() : null;
    }

    @JsonProperty("idUnite")
    public String getIdUnite() {
        if (idUniteCache != null) return idUniteCache;
        return uniteOrganisationnelle != null ? uniteOrganisationnelle.getIdUnite() : null;
    }

    @JsonProperty("idSociete")
    public String getIdSociete() {
        if (idSocieteCache != null) return idSocieteCache;
        return societe != null ? societe.getIdSociete() : null;
    }

    // Setters
    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public void setIdUniteCache(String idUnite) {
        this.idUniteCache = idUnite;
    }

    public void setIdSocieteCache(String idSociete) {
        this.idSocieteCache = idSociete;
    }

    public void setMatriculeCache(String matricule) {
        this.matriculeCache = matricule;
    }

    public void setNomCompletCache(String nomComplet) {
        this.nomCompletCache = nomComplet;
    }

    public void setUsernameCache(String username) {
        this.usernameCache = username;
    }

    public void setEmailCache(String email) {
        this.emailCache = email;
    }

    @PostLoad
    public void hydrateCache() {
        if (utilisateur != null) {
            this.utilisateurId = utilisateur.getId();
            this.matriculeCache = utilisateur.getMatricule();
            this.usernameCache = utilisateur.getUsername();
            this.emailCache = utilisateur.getEmail();
            this.nomCompletCache = utilisateur.getPrenom() + " " + utilisateur.getNom();
        }
        if (uniteOrganisationnelle != null) {
            this.idUniteCache = uniteOrganisationnelle.getIdUnite();
        }
        if (societe != null) {
            this.idSocieteCache = societe.getIdSociete();
        }
    }

    @Override
    public String toString() {
        return "EmployeSimple{" +
                "id=" + id +
                ", matricule='" + getMatricule() + '\'' +
                ", posteOccupe='" + posteOccupe + '\'' +
                ", departement='" + departement + '\'' +
                ", statut='" + statut + '\'' +
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