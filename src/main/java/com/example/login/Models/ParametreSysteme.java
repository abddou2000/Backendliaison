package com.example.login.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "parametre_systeme")
@Getter
@Setter
public class ParametreSysteme {

    @Id
    @Column(name = "id_parametre")
    private String id;

    // --- Paramètres généraux ---
    @Column(name = "fuseau_horaire")
    private String timezone;

    @Column(name = "format_date")
    private String dateFormat;

    @Column(name = "format_heure")
    private String timeFormat;

    @Column(name = "timeout_session")
    private Integer sessionTimeout;

    // --- Notifications ---
    @Column(name = "email_systeme")
    private String systemEmail;

    @Column(name = "message_notification", length = 500)
    private String notificationMessage;

    @Column(name = "notifications_activees")
    private Boolean enableSystemNotifications;

    // --- Sécurité et maintenance ---
    @Column(name = "sauvegarde_auto")
    private Boolean autoBackup;

    @Column(name = "mode_maintenance")
    private Boolean maintenanceMode;

    // --- Informations système ---
    @Column(name = "version_application")
    private String versionApplication;

    @Column(name = "date_derniere_maj")
    private LocalDateTime dateDerniereMiseAJour;

    @Column(name = "type_base_donnees")
    private String typeBDD;

    @Column(name = "uptime_systeme")
    private String uptimeSysteme;

    @Column(name = "nombre_utilisateurs_actifs")
    private Integer nombreUtilisateursActifs;

    // --- Métadonnées ---
    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @ManyToOne
    @JoinColumn(name = "modifie_par")
    private Administrateur modifiePar;

    // --- Constructeur par défaut ---
    public ParametreSysteme() {
        this.id = "PARAM_SYSTEM";
        this.timezone = "GMT+1";
        this.dateFormat = "JJ/MM/AAAA";
        this.timeFormat = "24h";
        this.sessionTimeout = 30;
        this.systemEmail = "notifications@innovex.ma";
        this.notificationMessage = "";
        this.enableSystemNotifications = true;
        this.autoBackup = true;
        this.maintenanceMode = false;
        this.versionApplication = "v2.1.4";
        this.dateDerniereMiseAJour = LocalDateTime.now();
        this.typeBDD = "PostgreSQL";
        this.uptimeSysteme = "99.9%";
        this.nombreUtilisateursActifs = 42;
        this.dateModification = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = "PARAM_SYSTEM";
        }
        this.dateModification = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.dateModification = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ParametreSysteme{" +
                "id='" + id + '\'' +
                ", timezone='" + timezone + '\'' +
                ", dateFormat='" + dateFormat + '\'' +
                ", timeFormat='" + timeFormat + '\'' +
                ", sessionTimeout=" + sessionTimeout +
                ", systemEmail='" + systemEmail + '\'' +
                ", enableSystemNotifications=" + enableSystemNotifications +
                ", autoBackup=" + autoBackup +
                ", maintenanceMode=" + maintenanceMode +
                '}';
    }
}