package com.example.login.repositories;  // doit correspondre au dossier Repositories

import com.example.login.models.TypeContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TypeContratRepository extends JpaRepository<TypeContrat, String> {
    Optional<TypeContrat> findByNomContrat(String nomContrat);
}
