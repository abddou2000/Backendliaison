package com.example.login.Repositories;

import com.example.login.Models.UniteOrganisationnelle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UniteOrganisationnelleRepository extends JpaRepository<UniteOrganisationnelle, String> {

    Optional<UniteOrganisationnelle> findByCodeUnite(String codeUnite);

    Optional<UniteOrganisationnelle> findByNomUnite(String nomUnite);

    List<UniteOrganisationnelle> findByTypeUnite(String typeUnite);

    List<UniteOrganisationnelle> findBySociete_IdSociete(String idSociete);

    List<UniteOrganisationnelle> findByUniteParent_IdUnite(String idUniteParent);
}
