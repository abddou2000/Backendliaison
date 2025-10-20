package com.example.login.Repositories;

import com.example.login.Models.EmployeSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeSimpleRepository extends JpaRepository<EmployeSimple, Long> {

    /**
     * ✅ MODIFIÉ : Trouver un employé par le matricule de son utilisateur
     */
    Optional<EmployeSimple> findByUtilisateurMatricule(String matricule);

    /**
     * ✅ MODIFIÉ : Vérifier si un matricule existe dans utilisateur
     */
    boolean existsByUtilisateurMatricule(String matricule);

    /**
     * Trouver les employés par département
     */
    List<EmployeSimple> findByDepartement(String departement);

    /**
     * Trouver les employés par poste occupé
     */
    List<EmployeSimple> findByPosteOccupe(String posteOccupe);

    /**
     * Trouver les employés par type de contrat
     */
    List<EmployeSimple> findByTypeContrat(String typeContrat);

    /**
     * Trouver les employés par genre
     */
    List<EmployeSimple> findByGenre(EmployeSimple.Genre genre);

    /**
     * Trouver les employés par situation familiale
     */
    List<EmployeSimple> findBySituationFamiliale(String situationFamiliale);

    /**
     * Trouver les employés par nombre d'enfants à charge
     */
    List<EmployeSimple> findByEnfantsACharge(Integer enfantsACharge);

    /**
     * Trouver les employés nés entre deux dates
     */
    List<EmployeSimple> findByDateNaissanceBetween(LocalDate dateDebut, LocalDate dateFin);

    /**
     * Trouver les employés par mois et jour de naissance (pour les anniversaires)
     */
    @Query("SELECT e FROM EmployeSimple e WHERE MONTH(e.dateNaissance) = :mois AND DAY(e.dateNaissance) = :jour")
    List<EmployeSimple> findByDateNaissanceMonthAndDateNaissanceDay(@Param("mois") int mois, @Param("jour") int jour);

    /**
     * Recherche par nom ou prénom via la relation utilisateur
     */
    List<EmployeSimple> findByUtilisateurNomContainingIgnoreCaseOrUtilisateurPrenomContainingIgnoreCase(
            String nom, String prenom);

    /**
     * Compter les employés par département
     */
    long countByDepartement(String departement);

    /**
     * Compter les employés par type de contrat
     */
    long countByTypeContrat(String typeContrat);

    /**
     * Obtenir tous les départements distincts
     */
    @Query("SELECT DISTINCT e.departement FROM EmployeSimple e WHERE e.departement IS NOT NULL ORDER BY e.departement")
    List<String> findDistinctDepartements();

    /**
     * Obtenir tous les postes distincts
     */
    @Query("SELECT DISTINCT e.posteOccupe FROM EmployeSimple e WHERE e.posteOccupe IS NOT NULL ORDER BY e.posteOccupe")
    List<String> findDistinctPostes();

    /**
     * Obtenir tous les types de contrat distincts
     */
    @Query("SELECT DISTINCT e.typeContrat FROM EmployeSimple e WHERE e.typeContrat IS NOT NULL ORDER BY e.typeContrat")
    List<String> findDistinctTypesContrat();

    /**
     * ✅ MODIFIÉ : Recherche générale par terme (nom, prénom, matricule dans utilisateur, département, poste)
     */
    @Query("SELECT e FROM EmployeSimple e WHERE " +
            "LOWER(e.utilisateur.matricule) LIKE LOWER(CONCAT('%', :terme, '%')) OR " +
            "LOWER(e.departement) LIKE LOWER(CONCAT('%', :terme, '%')) OR " +
            "LOWER(e.posteOccupe) LIKE LOWER(CONCAT('%', :terme, '%')) OR " +
            "LOWER(e.utilisateur.nom) LIKE LOWER(CONCAT('%', :terme, '%')) OR " +
            "LOWER(e.utilisateur.prenom) LIKE LOWER(CONCAT('%', :terme, '%'))")
    List<EmployeSimple> rechercherParTermeGeneral(@Param("terme") String terme);

    /**
     * Trouver les employés avec leurs informations utilisateur (JOIN FETCH pour éviter N+1)
     */
    @Query("SELECT e FROM EmployeSimple e JOIN FETCH e.utilisateur u ORDER BY u.nom, u.prenom")
    List<EmployeSimple> findAllWithUtilisateur();

    /**
     * Trouver un employé avec ses informations utilisateur par ID
     */
    @Query("SELECT e FROM EmployeSimple e JOIN FETCH e.utilisateur u WHERE e.id = :id")
    Optional<EmployeSimple> findByIdWithUtilisateur(@Param("id") Long id);

    /**
     * Trouver les employés d'un département avec leurs informations utilisateur
     */
    @Query("SELECT e FROM EmployeSimple e JOIN FETCH e.utilisateur u WHERE e.departement = :departement ORDER BY u.nom, u.prenom")
    List<EmployeSimple> findByDepartementWithUtilisateur(@Param("departement") String departement);

    /**
     * Statistiques par département
     */
    @Query("SELECT e.departement, COUNT(e) FROM EmployeSimple e WHERE e.departement IS NOT NULL GROUP BY e.departement ORDER BY COUNT(e) DESC")
    List<Object[]> getStatistiquesByDepartement();

    /**
     * Statistiques par type de contrat
     */
    @Query("SELECT e.typeContrat, COUNT(e) FROM EmployeSimple e WHERE e.typeContrat IS NOT NULL GROUP BY e.typeContrat ORDER BY COUNT(e) DESC")
    List<Object[]> getStatistiquesByTypeContrat();

    /**
     * Statistiques par genre
     */
    @Query("SELECT e.genre, COUNT(e) FROM EmployeSimple e WHERE e.genre IS NOT NULL GROUP BY e.genre")
    List<Object[]> getStatistiquesByGenre();

    /**
     * Employés ayant plus de X années d'expérience (basé sur la date de création du compte utilisateur)
     */
    @Query("SELECT e FROM EmployeSimple e WHERE e.utilisateur.dateCreation <= :dateLimit")
    List<EmployeSimple> findEmployesWithExperienceMoreThan(@Param("dateLimit") java.time.LocalDateTime dateLimit);

    /**
     * Recherche d'employés par email (via la relation utilisateur)
     */
    @Query("SELECT e FROM EmployeSimple e WHERE e.utilisateur.email = :email")
    Optional<EmployeSimple> findByEmail(@Param("email") String email);

    /**
     * Recherche d'employés par username (via la relation utilisateur)
     */
    @Query("SELECT e FROM EmployeSimple e WHERE e.utilisateur.username = :username")
    Optional<EmployeSimple> findByUsername(@Param("username") String username);

    /**
     * Compter les employés par tranche d'âge
     */
    @Query("SELECT " +
            "CASE " +
            "  WHEN YEAR(CURRENT_DATE) - YEAR(e.dateNaissance) < 25 THEN '< 25 ans' " +
            "  WHEN YEAR(CURRENT_DATE) - YEAR(e.dateNaissance) BETWEEN 25 AND 35 THEN '25-35 ans' " +
            "  WHEN YEAR(CURRENT_DATE) - YEAR(e.dateNaissance) BETWEEN 36 AND 45 THEN '36-45 ans' " +
            "  WHEN YEAR(CURRENT_DATE) - YEAR(e.dateNaissance) BETWEEN 46 AND 55 THEN '46-55 ans' " +
            "  ELSE '> 55 ans' " +
            "END AS trancheAge, " +
            "COUNT(e) " +
            "FROM EmployeSimple e " +
            "WHERE e.dateNaissance IS NOT NULL " +
            "GROUP BY " +
            "CASE " +
            "  WHEN YEAR(CURRENT_DATE) - YEAR(e.dateNaissance) < 25 THEN '< 25 ans' " +
            "  WHEN YEAR(CURRENT_DATE) - YEAR(e.dateNaissance) BETWEEN 25 AND 35 THEN '25-35 ans' " +
            "  WHEN YEAR(CURRENT_DATE) - YEAR(e.dateNaissance) BETWEEN 36 AND 45 THEN '36-45 ans' " +
            "  WHEN YEAR(CURRENT_DATE) - YEAR(e.dateNaissance) BETWEEN 46 AND 55 THEN '46-55 ans' " +
            "  ELSE '> 55 ans' " +
            "END " +
            "ORDER BY MIN(YEAR(CURRENT_DATE) - YEAR(e.dateNaissance))")
    List<Object[]> getStatistiquesByTrancheAge();
}