package com.example.login.Repositories;

import com.example.login.Models.TypeCotisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TypeCotisationRepository extends JpaRepository<TypeCotisation, String> {


    List<TypeCotisation> findByNomTypeCotisation(String nomTypeCotisation);


    /** 3. Recherche par code de type de cotisation exact (nouveau) */
    List<TypeCotisation> findByCodeTypeCotisation(String codeTypeCotisation);

    /** 4. Filtrer par date de début ≤ date ≤ date de fin (dateFin peut être null) */
    List<TypeCotisation> findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(Date start, Date end);

    /** 5. Filtrer par date de début après une date donnée */
    List<TypeCotisation> findByDateDebutAfter(Date date);

    /** 6. Filtrer par date de fin avant une date donnée */
    List<TypeCotisation> findByDateFinBefore(Date date);
}
