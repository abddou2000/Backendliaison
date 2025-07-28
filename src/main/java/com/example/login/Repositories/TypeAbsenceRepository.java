package com.example.login.Repositories;

import com.example.login.Models.TypeAbsence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TypeAbsenceRepository extends JpaRepository<TypeAbsence, String> {
    // Filtrer selon le nouveau booléen
    List<TypeAbsence> findByEssMss(Boolean essMss);

    // Filtres sur dates
    List<TypeAbsence> findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(Date start, Date end);
    List<TypeAbsence> findByDateDebutAfter(Date date);
    List<TypeAbsence> findByDateFinBefore(Date date);
}
