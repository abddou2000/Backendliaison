package com.example.login.Repositories;

import com.example.login.Models.EmployeSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeSimpleRepository extends JpaRepository<EmployeSimple, Long> {

    // ✅ Recherche par matricule de l'utilisateur lié
    Optional<EmployeSimple> findByUtilisateur_Matricule(String matricule);

    // ✅ Recherche par ID de l'utilisateur (utilisateur.id, pas utilisateurId)
    // Comme EmployeSimple.id = Utilisateur.id avec @MapsId, on peut chercher directement par l'id
    @Query("SELECT e FROM EmployeSimple e WHERE e.utilisateur.id = :utilisateurId")
    Optional<EmployeSimple> findByUtilisateurId(@Param("utilisateurId") Long utilisateurId);

    // ✅ Vérifier si un employé existe pour un utilisateur donné
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM EmployeSimple e WHERE e.utilisateur.id = :utilisateurId")
    boolean existsByUtilisateurId(@Param("utilisateurId") Long utilisateurId);

    // ✅ Recherche par département
    List<EmployeSimple> findByDepartement(String departement);

    // ✅ Recherche par poste occupé
    List<EmployeSimple> findByPosteOccupe(String posteOccupe);

    // ✅ Recherche par statut
    List<EmployeSimple> findByStatut(String statut);

    // ✅ Recherche par type de contrat
    List<EmployeSimple> findByTypeContrat(String typeContrat);

    // ✅ Recherche par équipe
    List<EmployeSimple> findByEquipe(String equipe);
}