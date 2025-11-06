package com.example.login.Services;

import com.example.login.Models.ActivityLog;
import com.example.login.Models.ActivityLog.ActivityStatus;
import com.example.login.Models.ActivityLog.ActivityType;
import com.example.login.Repositories.ActivityLogRepository;
import com.example.login.Specifications.ActivityLogSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ActivityLogService {

    @Autowired
    private ActivityLogRepository repository;

    // Créer une nouvelle activité
    public ActivityLog create(ActivityLog activity) {
        return repository.save(activity);
    }

    // Récupérer toutes les activités (avec pagination)
    public Page<ActivityLog> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        return repository.findAll(pageable);
    }

    // Récupérer une activité par ID
    public Optional<ActivityLog> getById(Long id) {
        return repository.findById(id);
    }

    // Recherche avec filtres avancés (utilise Specification pour PostgreSQL)
    public Page<ActivityLog> searchWithFilters(
            String search,
            String typeStr,
            String statusStr,
            String dateFilter,
            int page,
            int size
    ) {
        // Conversion des types
        ActivityType type = null;
        if (typeStr != null && !typeStr.trim().isEmpty() && !typeStr.equalsIgnoreCase("tous")) {
            try {
                type = ActivityType.valueOf(typeStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Type invalide, on ignore
            }
        }

        ActivityStatus status = null;
        if (statusStr != null && !statusStr.trim().isEmpty() && !statusStr.equalsIgnoreCase("tous")) {
            try {
                status = ActivityStatus.valueOf(statusStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Statut invalide, on ignore
            }
        }

        LocalDateTime startDate = calculateStartDate(dateFilter);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));

        // Utilisation de Specification pour éviter les problèmes PostgreSQL
        return repository.findAll(
                ActivityLogSpecification.withFilters(search, type, status, startDate),
                pageable
        );
    }

    // Calculer la date de début selon le filtre
    private LocalDateTime calculateStartDate(String dateFilter) {
        if (dateFilter == null || dateFilter.trim().isEmpty() || dateFilter.equalsIgnoreCase("all")) {
            return null;
        }

        LocalDateTime now = LocalDateTime.now();
        switch (dateFilter.toLowerCase()) {
            case "today":
                return now.toLocalDate().atStartOfDay();
            case "week":
                return now.minusDays(7);
            case "month":
                return now.minusMonths(1);
            default:
                return null;
        }
    }

    // Obtenir les statistiques
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();

        LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();

        stats.put("today", repository.countByTimestampAfter(today));
        stats.put("total", repository.count());
        stats.put("success", repository.countByStatus(ActivityStatus.SUCCESS));
        stats.put("errors", repository.countByStatus(ActivityStatus.ERROR));
        stats.put("users", repository.countDistinctUsers());

        return stats;
    }

    // Supprimer une activité
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Vérifier l'existence
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    // Récupérer les 50 dernières activités
    public List<ActivityLog> getRecent() {
        return repository.findTop50ByOrderByTimestampDesc();
    }

    // ✅ NOUVELLE MÉTHODE pour l'export (SANS pagination)
    public List<ActivityLog> getAllForExport(
            String search,
            ActivityType type,
            ActivityStatus status,
            LocalDateTime startDate
    ) {
        // Utiliser Specification sans pagination
        return repository.findAll(
                ActivityLogSpecification.withFilters(search, type, status, startDate),
                Sort.by(Sort.Direction.DESC, "timestamp")
        );
    }
}