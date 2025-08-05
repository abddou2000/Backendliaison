package com.example.login.Services;

import com.example.login.Models.Utilisateur;
import java.util.List;

public interface UtilisateurService {
    Utilisateur getById(Long id);
    Utilisateur getByUsername(String username);
    List<Utilisateur> getAll();
    Utilisateur create(Utilisateur user, String roleType);
    void updatePassword(Long userId, String rawPassword);
}
