// src/main/java/com/example/login/Repositories/RoleRepository.java
package com.example.login.repositories;

import com.example.login.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    // on ne cherche désormais que par "ADMIN"
    Optional<Role> findByIdRole(String idRole);
}
