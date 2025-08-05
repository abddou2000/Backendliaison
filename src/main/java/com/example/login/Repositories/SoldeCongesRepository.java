package com.example.login.Repositories;

import com.example.login.Models.SoldeConges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoldeCongesRepository extends JpaRepository<SoldeConges, String> {
    // CORRECTED METHOD 1: Renamed and changed parameter to Long
    List<SoldeConges> findByEmploye_Id(Long idEmploye);

    // CORRECTED METHOD 2: Renamed and changed parameter to Long
    SoldeConges findByEmploye_IdAndAnnee(Long idEmploye, Integer annee);
}