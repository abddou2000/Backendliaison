package com.example.login.Repositories;

import com.example.login.Models.ParametrageEtat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParametrageEtatRepository extends JpaRepository<ParametrageEtat, Long> {

    // Recherches utiles
    List<ParametrageEtat> findByNomFormulaire(String nomFormulaire);

    List<ParametrageEtat> findByVisibilite(Boolean visibilite);

    List<ParametrageEtat> findByNomFormulaireAndVisibilite(String nomFormulaire, Boolean visibilite);

    Optional<ParametrageEtat> findByNomFormulaireAndNomChamp(String nomFormulaire, String nomChamp);
}
