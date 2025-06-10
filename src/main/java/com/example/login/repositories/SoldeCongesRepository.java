package com.example.login.repositories;

import com.example.login.models.SoldeConges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoldeCongesRepository extends JpaRepository<SoldeConges, String> {
    List<SoldeConges> findByEmploye_IdEmploye(String idEmploye);
    SoldeConges findByEmploye_IdEmployeAndAnnee(String idEmploye, Integer annee);
}