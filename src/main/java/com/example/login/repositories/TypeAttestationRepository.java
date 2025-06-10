package com.example.login.repositories;

import com.example.login.models.TypeAttestation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeAttestationRepository extends JpaRepository<TypeAttestation, String> {
    /** Rechercher par nom exact */
    Optional<TypeAttestation> findByNomTypeAttestation(String nom);
}
