package com.example.login.repositories;

import com.example.login.models.Mesure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesureRepository extends JpaRepository<Mesure, String> {
    List<Mesure> findByEmploye_IdEmploye(String idEmploye);
    List<Mesure> findByTypeMesure_IdTypeMesure(String idTypeMesure);
}
