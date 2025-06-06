package com.example.login.Repositories;

import com.example.login.Models.TypeMesure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeMesureRepository extends JpaRepository<TypeMesure, String> {

    /** 1. Chercher par code exact */
    List<TypeMesure> findByCode(String code);

    /** 2. Chercher par nom exact */
    List<TypeMesure> findByNom(String nom);

    /** 3. Chercher les types d’embauche ou non */
    List<TypeMesure> findByEmbauche(Boolean embauche);
}
