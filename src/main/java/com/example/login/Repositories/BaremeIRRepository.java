package com.example.login.Repositories;

import com.example.login.Models.BaremeIR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaremeIRRepository extends JpaRepository<BaremeIR, String> {

    // Exemple : récupérer les tranches actives à une date donnée
    List<BaremeIR> findByDateDebutBeforeAndDateFinAfter(java.util.Date date1, java.util.Date date2);

    // Optionnel : récupérer les tranches associées à un configurateur spécifique
    List<BaremeIR> findByConfigurateur_IdConfigurateur(String idConfigurateur);
}
