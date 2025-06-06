package com.example.login.Repositories;

import com.example.login.Models.TypeAttestation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeAttestationRepository extends JpaRepository<TypeAttestation, String> {
    /** Rechercher par nom exact */
    Optional<TypeAttestation> findByNomTypeAttestation(String nom);
}
