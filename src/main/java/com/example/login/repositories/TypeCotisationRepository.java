package com.example.login.repositories;

import com.example.login.models.TypeCotisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TypeCotisationRepository extends JpaRepository<TypeCotisation, String> {

    List<TypeCotisation> findByNomCotisation(String nomCotisation);

    List<TypeCotisation> findByCodeCotisation(String codeCotisation);

    List<TypeCotisation> findByDateDebutBetween(Date start, Date end);

    List<TypeCotisation> findByDateDebutAfter(Date date);

    List<TypeCotisation> findByDateFinBefore(Date date);
}