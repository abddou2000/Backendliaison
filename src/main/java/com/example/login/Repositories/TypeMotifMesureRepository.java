package com.example.login.Repositories;

import com.example.login.Models.TypeMotifMesure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeMotifMesureRepository extends JpaRepository<TypeMotifMesure, String> {

    /** 1. Recherche par code exact */
    List<TypeMotifMesure> findByCode(String code);

    /** 2. Recherche par libellé exact */
    List<TypeMotifMesure> findByLibelle(String libelle);
}
