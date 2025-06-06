package com.example.login.Repositories;

import com.example.login.Models.Echelon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EchelonRepository extends JpaRepository<Echelon, String> {
    List<Echelon> findByCategorieSalariale_IdCategorie(String idCategorie);

    List<Echelon> findByStatutSalarial_IdStatut(String idStatut);

    List<Echelon> findByGrilleSalariale_IdGrille(String idGrille);
}
