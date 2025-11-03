package com.example.login.Services;

import com.example.login.Models.ActivityLog;
import com.example.login.Models.ActivityLog.ActivityStatus;
import com.example.login.Models.ActivityLog.ActivityType;
import com.example.login.Repositories.ActivityLogRepository;
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

    // Recherche avec filtres avancés
    public Page<ActivityLog> searchWithFilters(
            String search,
            String typeStr,
            String statusStr,
            String dateFilter,
            int page,
            int size
    ) {
        ActivityType type = typeStr != null && !typeStr.equals("tous")
                ? ActivityType.valueOf(typeStr.toUpperCase())
                : null;

        ActivityStatus status = statusStr != null && !statusStr.equals("tous")
                ? ActivityStatus.valueOf(statusStr.toUpperCase())
                : null;

        LocalDateTime startDate = calculateStartDate(dateFilter);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        return repository.findWithFilters(search, type, status, startDate, pageable);
    }

    // Calculer la date de début selon le filtre
    private LocalDateTime calculateStartDate(String dateFilter) {
        if (dateFilter == null || dateFilter.equals("all")) {
            return null;
        }

        LocalDateTime now = LocalDateTime.now();
        switch (dateFilter) {
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
}