package com.example.login.Repositories;

import com.example.login.Models.Mesure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesureRepository extends JpaRepository<Mesure, String> {
    // CORRECTED METHOD: Renamed and changed parameter to Long
    List<Mesure> findByEmploye_Id(Long idEmploye);

    List<Mesure> findByTypeMesure_IdTypeMesure(String idTypeMesure);
}