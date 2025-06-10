package com.example.login.repositories;

import com.example.login.models.ProfilSalarial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfilSalarialRepository extends JpaRepository<ProfilSalarial, String> {
    List<ProfilSalarial> findByCategorieSalariale_IdCategorie(String idCategorie);
    List<ProfilSalarial> findByStatutSalarial_IdStatut(String idStatut);
}
