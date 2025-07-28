// src/main/java/com/example/login/Repositories/TypeMesureRepository.java
package com.example.login.Repositories;

import com.example.login.Models.TypeMesure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TypeMesureRepository extends JpaRepository<TypeMesure, String> {
    List<TypeMesure> findByCode(String code);
    List<TypeMesure> findByNom(String nom);
    List<TypeMesure> findByEmbauche(Boolean embauche);

    // Recherche par période
    List<TypeMesure> findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(Date start, Date end);
    List<TypeMesure> findByDateDebutAfter(Date date);
    List<TypeMesure> findByDateFinBefore(Date date);
}
