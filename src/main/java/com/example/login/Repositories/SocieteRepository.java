// src/main/java/com/example/login/Repositories/SocieteRepository.java
package com.example.login.Repositories;

import com.example.login.Models.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface SocieteRepository extends JpaRepository<Societe, String> {
    List<Societe> findByCodeSociete(String codeSociete);
    List<Societe> findByVille(String ville);
    List<Societe> findByDateDebutLessThanEqualAndDateFinGreaterThanEqual(Date start, Date end);
    List<Societe> findByDateDebutAfter(Date date);
    List<Societe> findByDateFinBefore(Date date);
    List<Societe> findByNomBanque(String nomBanque);
}