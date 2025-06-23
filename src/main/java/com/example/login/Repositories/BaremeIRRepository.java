// src/main/java/com/example/login/Repositories/BaremeIRRepository.java
package com.example.login.Repositories;

import com.example.login.Models.BaremeIR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BaremeIRRepository extends JpaRepository<BaremeIR, String> {
    // récupérer les tranches actives à une date donnée
    List<BaremeIR> findByDateDebutBeforeAndDateFinAfter(Date date1, Date date2);
}