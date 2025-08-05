package com.example.login.Services;

import com.example.login.Models.Configurateur;
import com.example.login.Models.Utilisateur;
import com.example.login.Repositories.ConfigurateurRepository;
import com.example.login.Repositories.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConfigurateurServiceImpl implements ConfigurateurService {

    private final ConfigurateurRepository configurateurRepository;
    private final UtilisateurRepository utilisateurRepository;

    public ConfigurateurServiceImpl(
            ConfigurateurRepository configurateurRepository,
            UtilisateurRepository utilisateurRepository
    ) {
        this.configurateurRepository = configurateurRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public List<Configurateur> listAll() {
        return configurateurRepository.findAll();
    }

    @Override
    public Configurateur getById(Long id) {
        return configurateurRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Configurateur create(Configurateur configurateurData) {
        Long userId = configurateurData.getId();
        if (userId == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur est requis.");
        }

        // 1. Chercher l'utilisateur existant.
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + userId));

        // 2. Créer le nouveau profil.
        Configurateur newProfile = new Configurateur();
        newProfile.setRegion(configurateurData.getRegion());
        newProfile.setUtilisateur(utilisateur); // Lier l'utilisateur au profil
        newProfile.setId(userId); // Important pour les relations @MapsId

        // 3. Lier le profil à l'utilisateur.
        utilisateur.setConfigurateur(newProfile);

        // 4. Sauvegarder l'UTILISATEUR. La cascade s'occupera de sauvegarder le Configurateur.
        utilisateurRepository.save(utilisateur);

        return newProfile;
    }

    @Override
    public void delete(Long id) {
        // La suppression devrait aussi être gérée par la cascade.
        // Si vous supprimez un utilisateur, son profil configurateur devrait disparaître.
        utilisateurRepository.deleteById(id);
    }
}