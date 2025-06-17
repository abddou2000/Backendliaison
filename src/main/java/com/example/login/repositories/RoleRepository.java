package com.example.login.repositories;

import com.example.login.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    // Pour récupérer un rôle d’après son identifiant (champ idRole)
    Optional<Role> findByIdRole(String idRole);

    // Pour récupérer un rôle d’après son nom (champ nomRole)
    Optional<Role> findByNomRole(String nomRole);
}
