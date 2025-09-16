package com.example.login.Services;

import com.example.login.Models.EmployeSimple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeSimpleService {

    /**
     * Récupérer tous les employés avec pagination
     */
    Page<EmployeSimple> findAll(Pageable pageable);

    /**
     * Récupérer tous les employés sans pagination
     */
    List<EmployeSimple> findAll();

    /**
     * Trouver un employé par son ID
     */
    Optional<EmployeSimple> findById(Long id);

    /**
     * Trouver un employé par son matricule
     */
    Optional<EmployeSimple> findByMatricule(String matricule);

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
     * Trouver les employés nés entre deux dates
     */
    List<EmployeSimple> findByDateNaissanceBetween(LocalDate dateDebut, LocalDate dateFin);

    /**
     * Trouver les employés par nombre d'enfants à charge
     */
    List<EmployeSimple> findByEnfantsACharge(Integer enfantsACharge);

    /**
     * Recherche d'employés par nom ou prénom (via la relation utilisateur)
     */
    List<EmployeSimple> findByNomOrPrenom(String terme);

    /**
     * Sauvegarder ou mettre à jour un employé
     */
    EmployeSimple save(EmployeSimple employe);

    /**
     * Supprimer un employé par son ID
     */
    void deleteById(Long id);

    /**
     * Vérifier si un employé existe par son ID
     */
    boolean existsById(Long id);

    /**
     * Vérifier si un matricule existe déjà
     */
    boolean existsByMatricule(String matricule);

    /**
     * Compter le nombre total d'employés
     */
    long count();

    /**
     * Compter le nombre d'employés par département
     */
    long countByDepartement(String departement);

    /**
     * Compter le nombre d'employés par type de contrat
     */
    long countByTypeContrat(String typeContrat);

    /**
     * Obtenir tous les départements distincts
     */
    List<String> findDistinctDepartements();

    /**
     * Obtenir tous les postes distincts
     */
    List<String> findDistinctPostes();

    /**
     * Obtenir tous les types de contrat distincts
     */
    List<String> findDistinctTypesContrat();
}