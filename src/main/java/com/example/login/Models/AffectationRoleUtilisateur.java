package com.example.login.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "affectation_role_utilisateur")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AffectationRoleUtilisateur {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private Id id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    private Utilisateur utilisateur;

    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;

    @Embeddable
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    @EqualsAndHashCode
    public static class Id implements java.io.Serializable {
        @Column(name = "id_user", nullable = false)
        private Long userId;

        @Column(name = "id_role", nullable = false)
        private Long roleId;
    }
}
