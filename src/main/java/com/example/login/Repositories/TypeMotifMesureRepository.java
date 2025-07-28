// src/main/java/com/example/login/Repositories/TypeMotifMesureRepository.java
package com.example.login.Repositories;

import com.example.login.Models.TypeMotifMesure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TypeMotifMesureRepository extends JpaRepository<TypeMotifMesure, String> {
    List<TypeMotifMesure> findByCode(String code);
    List<TypeMotifMesure> findByLibelle(String libelle);

    List<TypeMotifMesure> findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(Date start, Date end);
    List<TypeMotifMesure> findByDateDebutAfter(Date date);
    List<TypeMotifMesure> findByDateFinBefore(Date date);
}
