package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Entity
@Table(name = "sauvegarde_bdd")
@Getter
@Setter
public class SauvegardeBDD {

    @Id
    @Column(name = "id_sauvegarde")
    private String id;

    @Column(name = "nom_fichier")
    private String nomFichier;

    @Column(name = "emplacement")
    private String emplacement;

    @Column(name = "date_sauvegarde")
    private LocalDateTime dateSauvegarde;

    @Column(name = "taille_fichier")
    private Long tailleFichier; // en octets

    @Enumerated(EnumType.STRING)
    @Column(name = "cree_par_type")
    private TypeCreation createdBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatutSauvegarde status;

    @ManyToOne
    @JoinColumn(name = "cree_par")
    private Administrateur creePar;

    // Enums pour le frontend
    public enum TypeCreation {
        Automatique,
        Manuel
    }

    public enum StatutSauvegarde {
        success,
        pending,
        failed
    }

    // Constructors
    public SauvegardeBDD() {
        this.dateSauvegarde = LocalDateTime.now();
        this.status = StatutSauvegarde.pending;
    }

    public SauvegardeBDD(String id, String nomFichier, String emplacement,
                         Administrateur creePar, TypeCreation createdBy) {
        this.id = id;
        this.nomFichier = nomFichier;
        this.emplacement = emplacement;
        this.dateSauvegarde = LocalDateTime.now();
        this.creePar = creePar;
        this.createdBy = createdBy;
        this.status = StatutSauvegarde.pending;
    }

    // Méthodes pour le frontend - FORMAT EXACT
    public String getDate() {
        // ✅ CORRECTION : Ajout du point après le mois
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM. yyyy", Locale.FRENCH);
        return dateSauvegarde.format(formatter);
    }

    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateSauvegarde.format(formatter);
    }

    public String getSize() {
        if (tailleFichier == null) return "0 MB";
        double sizeMB = tailleFichier / (1024.0 * 1024.0);
        return String.format("%.1f MB", sizeMB);
    }

    @Override
    public String toString() {
        return "SauvegardeBDD{" +
                "id='" + id + '\'' +
                ", nomFichier='" + nomFichier + '\'' +
                ", dateSauvegarde=" + dateSauvegarde +
                ", tailleFichier=" + tailleFichier +
                ", createdBy=" + createdBy +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SauvegardeBDD)) return false;
        SauvegardeBDD that = (SauvegardeBDD) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}