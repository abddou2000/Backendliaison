package com.example.login.Services;

import com.example.login.Models.Rh;
import java.util.List;
import java.util.Optional;

public interface RhService {

    /**
     * Crée un nouveau profil RH pour un utilisateur existant.
     * @param rhData Les données du profil (ID utilisateur et services).
     * @return Le profil RH créé et sauvegardé.
     */
    Rh create(Rh rhData);

    /**
     * Récupère un profil RH par son ID.
     * @param id L'ID de l'utilisateur.
     * @return Un Optional contenant le profil RH s'il existe.
     */
    Optional<Rh> getById(Long id);

    /**
     * Récupère la liste de tous les profils RH.
     * @return Une liste de tous les profils RH.
     */
    List<Rh> getAll();

    /**
     * Supprime un utilisateur et son profil RH associé.
     * @param id L'ID de l'utilisateur à supprimer.
     */
    void delete(Long id);
}