package com.example.login.repositories;

import com.example.login.models.SauvegardeBDD;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SauvegardeBDDRepository extends JpaRepository<SauvegardeBDD, String> {

    /**
     * Récupérer toutes les sauvegardes créées par un administrateur donné
     */
    List<SauvegardeBDD> findByCreePar_IdAdministrateur(String idAdmin);
}
