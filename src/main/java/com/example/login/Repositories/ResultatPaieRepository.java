package com.example.login.Repositories;

import com.example.login.Models.ResultatPaie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultatPaieRepository extends JpaRepository<ResultatPaie, String> {
    // CORRECTED METHOD: Renamed and changed parameter to Long
    List<ResultatPaie> findByEmploye_Id(Long idEmploye);

    List<ResultatPaie> findByPeriodePaie_IdPeriode(String idPeriode);
    List<ResultatPaie> findBySociete_IdSociete(String idSociete);
}