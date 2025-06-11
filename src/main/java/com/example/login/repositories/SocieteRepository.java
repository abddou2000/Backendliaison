package com.example.login.repositories;

import com.example.login.models.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocieteRepository extends JpaRepository<Societe, String> {

    // Plus besoin de cette méthode car findById existe déjà dans JpaRepository :
    // Optional<Societe> findByIdSociete(String idSociete); ❌

    // Nouveau champ : nomSociete (remplace raisonSociale)
    Optional<Societe> findByNomSociete(String nomSociete);
}
