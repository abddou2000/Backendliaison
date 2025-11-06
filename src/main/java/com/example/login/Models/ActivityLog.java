package com.example.login.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity_log")
@Getter @Setter
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    // Informations utilisateur (dénormalisées pour performance)
    @JsonProperty("userId")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @JsonProperty("userName")
    @Column(name = "user_name", nullable = false)
    private String userName;

    @JsonProperty("userEmail")
    @Column(name = "user_email", nullable = false)
    private String userEmail;

    private String action;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @Enumerated(EnumType.STRING)
    private ActivityStatus status;

    private String target;

    @JsonProperty("ipAddress")
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @JsonProperty("userAgent")
    @Column(name = "user_agent", length = 1000)
    private String userAgent;

    private String details;

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }

    // Enums
    public enum ActivityType {
        CONNEXION, CREATION, MODIFICATION, SUPPRESSION, EXPORT, CONFIGURATION
    }

    public enum ActivityStatus {
        SUCCESS, WARNING, ERROR
    }
}