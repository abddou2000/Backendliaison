package com.example.login.Services.Impl;

import com.example.login.Models.Administrateur;
import com.example.login.Models.ParametreSysteme;
import com.example.login.Repositories.ParametreSystemeRepository;
import com.example.login.Services.ParametreSystemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ParametreSystemeServiceImpl implements ParametreSystemeService {

    @Autowired
    private ParametreSystemeRepository parametreRepository;

    @Override
    public ParametreSysteme getParametres() {
        return parametreRepository.findFirstByOrderByDateModificationDesc()
                .orElseGet(() -> {
                    ParametreSysteme defaut = new ParametreSysteme();
                    return parametreRepository.save(defaut);
                });
    }

    @Override
    public ParametreSysteme updateParametres(ParametreSysteme parametres, Administrateur admin) {
        ParametreSysteme existant = getParametres();

        existant.setTimezone(parametres.getTimezone());
        existant.setDateFormat(parametres.getDateFormat());
        existant.setTimeFormat(parametres.getTimeFormat());
        existant.setSessionTimeout(parametres.getSessionTimeout());
        existant.setSystemEmail(parametres.getSystemEmail());
        existant.setNotificationMessage(parametres.getNotificationMessage());
        existant.setEnableSystemNotifications(parametres.getEnableSystemNotifications());
        existant.setAutoBackup(parametres.getAutoBackup());
        existant.setMaintenanceMode(parametres.getMaintenanceMode());
        existant.setModifiePar(admin);
        existant.setDateModification(LocalDateTime.now());

        return parametreRepository.save(existant);
    }

    @Override
    public ParametreSysteme resetParametres(Administrateur admin) {
        ParametreSysteme existant = getParametres();

        existant.setTimezone("GMT+1");
        existant.setDateFormat("JJ/MM/AAAA");
        existant.setTimeFormat("24h");
        existant.setSessionTimeout(30);
        existant.setSystemEmail("notifications@innovex.ma");
        existant.setNotificationMessage("");
        existant.setEnableSystemNotifications(true);
        existant.setAutoBackup(true);
        existant.setMaintenanceMode(false);
        existant.setModifiePar(admin);
        existant.setDateModification(LocalDateTime.now());

        return parametreRepository.save(existant);
    }

    @Override
    public Map<String, Object> getSystemStats() {
        ParametreSysteme params = getParametres();

        Map<String, Object> stats = new HashMap<>();
        stats.put("utilisateursActifs", params.getNombreUtilisateursActifs());
        stats.put("sauvegardeAuto", params.getAutoBackup() ? "Activée" : "Désactivée");
        stats.put("sessionTimeout", params.getSessionTimeout() + " min");
        stats.put("securite", "Optimale");
        stats.put("versionApplication", params.getVersionApplication());
        stats.put("derniereMiseAJour", "15 Juil 2024");
        stats.put("baseDeDonnees", params.getTypeBDD());
        stats.put("uptimeSysteme", params.getUptimeSysteme());

        return stats;
    }
}