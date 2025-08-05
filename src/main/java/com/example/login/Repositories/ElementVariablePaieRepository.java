package com.example.login.Repositories;

import com.example.login.Models.ElementVariablePaie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElementVariablePaieRepository extends JpaRepository<ElementVariablePaie, Long> {
    // CORRECTED METHOD: Renamed and changed parameter to Long
    List<ElementVariablePaie> findByEmploye_Id(Long idEmploye);

    List<ElementVariablePaie> findByPeriodePaie_IdPeriode(String idPeriode);
    List<ElementVariablePaie> findByRubriquePaie_IdRubrique(String idRubrique);
}