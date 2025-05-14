package com.example.login.Repositories;

import com.example.login.Models.Attestation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttestationRepository extends JpaRepository<Attestation, String> {
    Optional<Attestation> findByNomAttestation(String nomAttestation);
}
