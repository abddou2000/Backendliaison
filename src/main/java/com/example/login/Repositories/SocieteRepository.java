package com.example.login.Repositories;

import com.example.login.Models.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocieteRepository extends JpaRepository<Societe, String> {
    Optional<Societe> findByIdSociete(String idSociete);
    Optional<Societe> findByRaisonSociale(String raisonSociale);
}