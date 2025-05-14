package com.example.login.Repositories;

import com.example.login.Models.TypeContrat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeContratRepository extends JpaRepository<TypeContrat, String> {
    Optional<TypeContrat> findByNomContrat(String nomContrat);
}
