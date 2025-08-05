package com.example.login.Repositories;

import com.example.login.Models.FichePaie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FichePaieRepository extends JpaRepository<FichePaie, String> {
    // CORRECTED METHOD: Renamed and changed parameter to Long
    List<FichePaie> findByEmploye_Id(Long idEmploye);

    List<FichePaie> findByPeriodePaie_IdPeriode(String idPeriode);
    List<FichePaie> findByPeriodeAnneeAndPeriodeMois(Integer annee, Integer mois);
}