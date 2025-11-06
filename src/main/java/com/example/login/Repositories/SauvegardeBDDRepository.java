package com.example.login.Repositories;

import com.example.login.Models.SauvegardeBDD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour l'entité SauvegardeBDD.
 * Gère toutes les opérations de base de données (CRUD) et les requêtes personnalisées.
 */
@Repository
public interface SauvegardeBDDRepository extends JpaRepository<SauvegardeBDD, String> {

    /**
     * Trouve toutes les sauvegardes effectuées avant une date limite.
     * Utilisé par la fonction de nettoyage pour supprimer les anciennes archives.
     * Trié par date pour potentiellement supprimer les plus anciennes en priorité.
     *
     * @param dateLimite La date de comparaison.
     * @return Une liste de sauvegardes.
     */
    List<SauvegardeBDD> findByDateSauvegardeBeforeOrderByDateSauvegardeDesc(LocalDateTime dateLimite);

    /**
     * Recherche les sauvegardes dont le nom de fichier contient une chaîne de caractères donnée.
     * C'est la méthode qui alimente la barre de recherche du frontend.
     * La recherche est insensible à la casse et les résultats sont triés par date décroissante.
     *
     * @param query La chaîne de caractères à rechercher (par exemple, une partie d'une date comme "20251031").
     * @return Une liste de sauvegardes correspondantes.
     */
    List<SauvegardeBDD> findByNomFichierContainingIgnoreCaseOrderByDateSauvegardeDesc(String query);
}