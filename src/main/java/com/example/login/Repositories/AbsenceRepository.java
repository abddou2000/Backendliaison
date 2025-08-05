package com.example.login.Repositories;

import com.example.login.Models.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, String> {
    // CORRECTED METHOD: Renamed and changed parameter to Long
    List<Absence> findByEmploye_Id(Long idEmploye);

    List<Absence> findByTypeAbsence_IdTypeAbsence(String idTypeAbsence);
    List<Absence> findByPeriodePaie_IdPeriode(String idPeriode);
}