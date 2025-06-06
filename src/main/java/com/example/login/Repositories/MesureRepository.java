package com.example.login.Repositories;

import com.example.login.Models.Mesure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesureRepository extends JpaRepository<Mesure, String> {
    List<Mesure> findByEmploye_IdEmploye(String idEmploye);
    List<Mesure> findByTypeMesure_IdTypeMesure(String idTypeMesure);
}
