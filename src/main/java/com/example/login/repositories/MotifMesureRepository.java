package com.example.login.repositories;

import com.example.login.models.MotifMesure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotifMesureRepository extends JpaRepository<MotifMesure, String> {
    List<MotifMesure> findByMesure_IdMesure(String idMesure);
    List<MotifMesure> findByTypeMotifMesure_IdTypeMotifMesure(String idTypeMotifMesure);
}
