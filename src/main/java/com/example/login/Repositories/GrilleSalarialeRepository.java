package com.example.login.Repositories;

import com.example.login.Models.GrilleSalariale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrilleSalarialeRepository extends JpaRepository<GrilleSalariale, String> {

    Optional<GrilleSalariale> findByCodeGrille(String codeGrille);

    List<GrilleSalariale> findByEchelons_Id(String id); // si besoin de chercher par échelon
}
