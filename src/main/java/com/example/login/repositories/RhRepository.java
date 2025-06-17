package com.example.login.repositories;

import com.example.login.models.Rh;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RhRepository extends JpaRepository<Rh, String> {
    Optional<Rh> findByEmail(String email);
}
