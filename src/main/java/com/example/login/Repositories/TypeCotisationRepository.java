package com.example.login.Repositories;

import com.example.login.Models.TypeCotisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TypeCotisationRepository extends JpaRepository<TypeCotisation, String> {

    /** 1. Recherche par nom exact */
    List<TypeCotisation> findByNomCotisation(String nom);

    /** 2. Recherche par code exact */
    List<TypeCotisation> findByCodeCotisation(String code);

    /** 3. Filtrer par date de début ≤ date ≤ date de fin (dateFin peut être null) */
    List<TypeCotisation> findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(Date start, Date end);

    /** 4. Filtrer par date de début après une date donnée */
    List<TypeCotisation> findByDateDebutAfter(Date date);

    /** 5. Filtrer par date de fin avant une date donnée */
    List<TypeCotisation> findByDateFinBefore(Date date);
}
