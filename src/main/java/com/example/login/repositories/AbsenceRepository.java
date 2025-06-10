package com.example.login.repositories;

import com.example.login.models.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, String> {
    List<Absence> findByEmploye_IdEmploye(String idEmploye);
    List<Absence> findByTypeAbsence_IdTypeAbsence(String idTypeAbsence);
    List<Absence> findByPeriodePaie_IdPeriode(String idPeriode);
}