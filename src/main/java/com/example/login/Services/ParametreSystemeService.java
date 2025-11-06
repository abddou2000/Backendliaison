package com.example.login.Services;

import com.example.login.Models.Administrateur;
import com.example.login.Models.ParametreSysteme;

import java.util.Map;

public interface ParametreSystemeService {

    /**
     * Récupère les paramètres système
     */
    ParametreSysteme getParametres();

    /**
     * Met à jour les paramètres système
     */
    ParametreSysteme updateParametres(ParametreSysteme parametres, Administrateur admin);

    /**
     * Réinitialise les paramètres aux valeurs par défaut
     */
    ParametreSysteme resetParametres(Administrateur admin);

    /**
     * Récupère les statistiques système
     */
    Map<String, Object> getSystemStats();
}