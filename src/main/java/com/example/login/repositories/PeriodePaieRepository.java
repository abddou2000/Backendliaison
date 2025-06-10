package com.example.login.repositories;

import com.example.login.models.PeriodePaie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodePaieRepository extends JpaRepository<PeriodePaie, String> {
    List<PeriodePaie> findByAnnee(Integer annee);
    List<PeriodePaie> findByEtatPeriode(String etat);
}
