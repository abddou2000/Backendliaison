package com.example.login.Services;

import com.example.login.Models.Utilisateur;
import java.util.List;

public interface UtilisateurService {
    Utilisateur getById(Long id);
    Utilisateur getByUsername(String username);
    List<Utilisateur> getAll();
    Utilisateur create(Utilisateur user, String roleType);
    void updatePassword(Long userId, String ancienMdp, String nouveauMdp, String confirmationMdp);
    void demanderReinitialisation(String email);
    void reinitialiserMdp(String token, String nouveauMdp, String confirmationMdp);

    // NOUVELLE MÉTHODE AJOUTÉE
    List<Utilisateur> getByRole(String roleType);
}