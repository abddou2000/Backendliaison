package com.example.login.repositories;

import com.example.login.models.JourFerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JourFerieRepository extends JpaRepository<JourFerie, String> {
    List<JourFerie> findByParametreur_IdConfigurateur(String idParametreur);
    List<JourFerie> findByRecurrenceAnnuelleTrue();
}
