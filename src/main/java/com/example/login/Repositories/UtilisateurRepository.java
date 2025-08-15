package com.example.login.Repositories;

import com.example.login.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByUsername(String username);
    Optional<Utilisateur> findByEmail(String email);

    // --- MÉTHODE À AJOUTER ---
    Optional<Utilisateur> findByTokenReinitialisation(String token);
}