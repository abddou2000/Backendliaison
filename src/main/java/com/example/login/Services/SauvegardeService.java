package com.example.login.Services;

import com.example.login.Models.Administrateur;
import com.example.login.Models.SauvegardeBDD;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SauvegardeService {

    List<SauvegardeBDD> findAll();

    Map<String, Object> getDashboardStats();

    List<SauvegardeBDD> search(String query);

    SauvegardeBDD lancerSauvegardeManuelle(Administrateur admin);

    void restaurerSauvegarde(String backupId) throws Exception;

    Resource chargerFichierEnTantQueResource(String id) throws Exception;

    void deleteByIds(List<String> ids);

    void lancerSauvegardeAutomatique();

    SauvegardeBDD executerSauvegarde(SauvegardeBDD.TypeCreation typeCreation, Administrateur admin);

    Optional<SauvegardeBDD> findById(String id);
}