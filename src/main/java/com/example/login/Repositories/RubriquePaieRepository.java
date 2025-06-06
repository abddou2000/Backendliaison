package com.example.login.Repositories;

import com.example.login.Models.RubriquePaie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RubriquePaieRepository extends JpaRepository<RubriquePaie, String> {

    /**
     * 1. Chercher une rubrique par son code
     */
    List<RubriquePaie> findByCodeRubrique(String codeRubrique);

    /**
     * 2. Chercher les rubriques d’un certain type (ex. "PRIME", "RETRAITE", etc.)
     */
    List<RubriquePaie> findByTypeRubrique(String typeRubrique);

    /**
     * 3. Filtrer les rubriques actives à une date donnée
     *    (dateDebut ≤ date ≤ dateFin ou dateFin est null)
     */
    List<RubriquePaie> findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(Date dateDebut, Date dateFin);

    /**
     * 4. Filtrer les rubriques dont la date de fin est postérieure à une date
     */
    List<RubriquePaie> findByDateFinAfter(Date date);

    /**
     * 5. Rechercher par libellé partiel (LIKE)
     */
    List<RubriquePaie> findByLibelleContainingIgnoreCase(String fragment);
}
