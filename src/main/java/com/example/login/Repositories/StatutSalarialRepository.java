package com.example.login.Repositories;

import com.example.login.Models.StatutSalarial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatutSalarialRepository extends JpaRepository<StatutSalarial, String> {

    Optional<StatutSalarial> findByCodeStatut(String codeStatut);

    Optional<StatutSalarial> findByNomStatut(String nomStatut);
}
