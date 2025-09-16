package com.example.login.Security;

import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public CustomUserDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        System.out.println("=== CustomUserDetailsService DEBUG ===");
        System.out.println("Recherche utilisateur: " + usernameOrEmail);

        // Rechercher par username ou email
        Utilisateur utilisateur = utilisateurRepository
                .findByUsername(usernameOrEmail)
                .or(() -> utilisateurRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> {
                    System.err.println("Utilisateur non trouvé: " + usernameOrEmail);
                    return new UsernameNotFoundException("Utilisateur introuvable : " + usernameOrEmail);
                });

        System.out.println("Utilisateur trouvé: " + utilisateur.getUsername());
        System.out.println("Email: " + utilisateur.getEmail());
        System.out.println("Hash du mot de passe: " + utilisateur.getPasswordHash());
        System.out.println("Rôle utilisateur: " + (utilisateur.getRole() != null ? utilisateur.getRole().getType() : "NULL"));
        System.out.println("État du compte: " + utilisateur.getEtatCompte());

        // Vérifier que l'utilisateur a un rôle
        if (utilisateur.getRole() == null) {
            System.err.println("ERREUR: Utilisateur sans rôle assigné!");
            throw new UsernameNotFoundException("Utilisateur sans rôle assigné : " + usernameOrEmail);
        }

        // Construire les autorités avec le préfixe ROLE_ obligatoire
        Collection<GrantedAuthority> authorities = getAuthorities(utilisateur);
        System.out.println("Autorités créées: " + authorities);

        // Déterminer l'état du compte
        boolean accountNonExpired = true;
        boolean accountNonLocked = !Utilisateur.EtatCompte.BLOQUE.equals(utilisateur.getEtatCompte());
        boolean credentialsNonExpired = isCredentialsNonExpired(utilisateur);
        boolean enabled = isAccountEnabled(utilisateur);

        System.out.println("État du compte:");
        System.out.println("  - Compte non expiré: " + accountNonExpired);
        System.out.println("  - Compte non bloqué: " + accountNonLocked);
        System.out.println("  - Identifiants non expirés: " + credentialsNonExpired);
        System.out.println("  - Compte activé: " + enabled);

        // Créer et retourner l'objet UserDetails
        UserDetails userDetails = User.builder()
                .username(utilisateur.getUsername())
                .password(utilisateur.getPasswordHash())
                .authorities(authorities)
                .accountExpired(!accountNonExpired)
                .accountLocked(!accountNonLocked)
                .credentialsExpired(!credentialsNonExpired)
                .disabled(!enabled)
                .build();

        System.out.println("UserDetails créé avec succès pour: " + utilisateur.getUsername());
        return userDetails;
    }

    /**
     * Construire les autorités (rôles) pour l'utilisateur
     */
    private Collection<GrantedAuthority> getAuthorities(Utilisateur utilisateur) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (utilisateur.getRole() != null && utilisateur.getRole().getType() != null) {
            // CRITIQUE: Ajouter le préfixe ROLE_ obligatoire pour Spring Security
            String roleWithPrefix = "ROLE_" + utilisateur.getRole().getType().toUpperCase();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleWithPrefix);
            authorities.add(authority);

            System.out.println("Autorité ajoutée: " + roleWithPrefix);

            // Optionnel: Ajouter des permissions supplémentaires basées sur le rôle
            addRoleBasedPermissions(authorities, utilisateur.getRole().getType());
        } else {
            System.err.println("ATTENTION: Utilisateur sans rôle valide!");
        }

        return authorities;
    }

    /**
     * Ajouter des permissions supplémentaires basées sur le rôle
     */
    private void addRoleBasedPermissions(List<GrantedAuthority> authorities, String roleType) {
        switch (roleType.toUpperCase()) {
            case "ADMIN":
                authorities.add(new SimpleGrantedAuthority("PERMISSION_READ_ALL"));
                authorities.add(new SimpleGrantedAuthority("PERMISSION_WRITE_ALL"));
                authorities.add(new SimpleGrantedAuthority("PERMISSION_DELETE_ALL"));
                break;
            case "RH":
                authorities.add(new SimpleGrantedAuthority("PERMISSION_READ_EMPLOYEES"));
                authorities.add(new SimpleGrantedAuthority("PERMISSION_WRITE_EMPLOYEES"));
                authorities.add(new SimpleGrantedAuthority("PERMISSION_READ_REPORTS"));
                break;
            case "EMPLOYE":
                authorities.add(new SimpleGrantedAuthority("PERMISSION_READ_OWN_DATA"));
                authorities.add(new SimpleGrantedAuthority("PERMISSION_WRITE_OWN_DATA"));
                break;
            case "CONFIGURATEUR":
                authorities.add(new SimpleGrantedAuthority("PERMISSION_CONFIGURE_SYSTEM"));
                authorities.add(new SimpleGrantedAuthority("PERMISSION_READ_CONFIG"));
                break;
            default:
                System.out.println("Rôle non reconnu pour les permissions: " + roleType);
        }
    }

    /**
     * Vérifier si le compte est activé
     */
    private boolean isAccountEnabled(Utilisateur utilisateur) {
        Utilisateur.EtatCompte etat = utilisateur.getEtatCompte();
        return etat == Utilisateur.EtatCompte.ACTIF ||
                etat == Utilisateur.EtatCompte.EN_ATTENTE_CHANGEMENT_MDP;
    }

    /**
     * Vérifier si les identifiants ne sont pas expirés
     */
    private boolean isCredentialsNonExpired(Utilisateur utilisateur) {
        LocalDateTime dateExpiration = utilisateur.getDateExpirationMdp();

        if (dateExpiration == null) {
            // Si pas de date d'expiration définie, les identifiants ne sont pas expirés
            return true;
        }

        // Vérifier si la date d'expiration est dans le futur
        return dateExpiration.isAfter(LocalDateTime.now());
    }

    /**
     * Méthode utilitaire pour obtenir un utilisateur par username/email
     * (peut être utilisée ailleurs dans l'application)
     */
    public Utilisateur getUtilisateur(String usernameOrEmail) {
        return utilisateurRepository
                .findByUsername(usernameOrEmail)
                .or(() -> utilisateurRepository.findByEmail(usernameOrEmail))
                .orElse(null);
    }

    /**
     * Vérifier si un utilisateur existe
     */
    public boolean userExists(String usernameOrEmail) {
        return getUtilisateur(usernameOrEmail) != null;
    }
}