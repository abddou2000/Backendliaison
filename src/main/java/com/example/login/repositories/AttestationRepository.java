package com.example.login.repositories;

import com.example.login.models.Attestation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttestationRepository extends JpaRepository<Attestation, String> {
    Optional<Attestation> findByNomAttestation(String nomAttestation);
}
