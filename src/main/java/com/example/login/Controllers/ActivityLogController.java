package com.example.login.Controllers;

import com.example.login.Models.ActivityLog;
import com.example.login.Services.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activity-logs")
@CrossOrigin(origins = "*")
public class ActivityLogController {

    @Autowired
    private ActivityLogService service;

    // Créer une nouvelle activité
    @PostMapping
    public ResponseEntity<ActivityLog> create(@RequestBody ActivityLog activity) {
        return ResponseEntity.ok(service.create(activity));
    }

    // Liste paginée avec filtres
    @GetMapping
    public ResponseEntity<Page<ActivityLog>> list(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "tous") String type,
            @RequestParam(defaultValue = "tous") String status,
            @RequestParam(defaultValue = "week") String dateFilter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ActivityLog> activities = service.searchWithFilters(search, type, status, dateFilter, page, size);
        return ResponseEntity.ok(activities);
    }

    // Récupérer une activité par ID
    @GetMapping("/{id}")
    public ResponseEntity<ActivityLog> get(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtenir les statistiques
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        return ResponseEntity.ok(service.getStatistics());
    }

    // Récupérer les activités récentes
    @GetMapping("/recent")
    public ResponseEntity<List<ActivityLog>> getRecent() {
        return ResponseEntity.ok(service.getRecent());
    }

    // Supprimer une activité
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}