package com.example.login.Security;

import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- 1. Importation ajoutée

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public CustomUserDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    @Transactional(readOnly = true) // <-- 2. Annotation ajoutée
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Utilisateur user = utilisateurRepository
                .findByUsername(usernameOrEmail)
                .or(() -> utilisateurRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Utilisateur introuvable : " + usernameOrEmail));

        // Crée une autorité correspondant au type de rôle (ADMIN, RH, etc.)
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(user.getRole().getType());

        // Retourne un User Spring Security avec username, mot de passe encodé et autorités
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                Collections.singletonList(authority)
        );
    }
}