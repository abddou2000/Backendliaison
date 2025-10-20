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
    List<Utilisateur> getByRole(String roleType);
    Utilisateur updateUtilisateurDetails(Long id, String nom, String prenom, String email, String username, String matricule, Boolean actif);
    void adminResetPassword(Long userId, String nouveauMdp);
    void deleteUtilisateur(Long userId);
}