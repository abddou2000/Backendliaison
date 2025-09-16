package com.example.login.Services;

import com.example.login.Models.SauvegardeBDD;

import java.util.List;

public interface SauvegardeBDDService {

    List<SauvegardeBDD> getAllSauvegardes();

    SauvegardeBDD creerSauvegardeManuelle(Long adminId);

    /**
     * MODIFIÉ : Supprime toutes les archives après avoir validé le mot de passe de l'admin.
     * @param adminUsername Le nom d'utilisateur de l'admin qui effectue l'action.
     * @param password Le mot de passe fourni pour vérification.
     */
    void nettoyerToutesLesArchives(String adminUsername, String password);
}