package com.example.login.Repositories;

import com.example.login.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByUsername(String username);

    Optional<Utilisateur> findByEmail(String email);

    Optional<Utilisateur> findByTokenReinitialisation(String token);

    List<Utilisateur> findByRole_Type(String type);

    // ✅ NOUVELLE MÉTHODE : Pour la vérification d'unicité du matricule
    Optional<Utilisateur> findByMatricule(String matricule);
}