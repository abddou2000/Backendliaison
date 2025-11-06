package com.example.login.Repositories;

import com.example.login.Models.Administrateur;
import com.example.login.Models.ParametreSysteme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParametreSystemeRepository extends JpaRepository<ParametreSysteme, String> {
    public interface AdministrateurRepository extends JpaRepository<Administrateur, String> {
        Optional<Administrateur> findByUsername(String username);
    }
    /**
     * Récupère les paramètres système (il n'y a qu'un seul enregistrement)
     * public interface AdministrateurRepository extends JpaRepository<Administrateur, String> {
     *     Optional<Administrateur> findByUsername(String username);
     * }
     */
    Optional<ParametreSysteme> findFirstByOrderByDateModificationDesc();
}