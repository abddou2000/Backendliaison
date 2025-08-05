package com.example.login.Services;

import com.example.login.Models.Administrateur;
import java.util.List;

public interface AdministrateurService {
    List<Administrateur> getAllAdministrateurs();

    Administrateur getById(Long id);

    // --- SIGNATURE DE LA MÉTHODE CORRIGÉE ---
    // La création du profil Administrateur ne nécessite plus le 'roleType'.
    // Cette responsabilité appartient à la création de l'Utilisateur.
    Administrateur create(Administrateur administrateur);

    void delete(Long id);
}