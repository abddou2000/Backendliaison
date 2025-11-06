package com.example.login.Controllers;

import com.example.login.Models.ActivityLog;
import com.example.login.Services.ActivityLogService;
import com.example.login.Services.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activity-logs")
@CrossOrigin(origins = "*")
public class ActivityLogController {

    @Autowired
    private ActivityLogService service;

    @Autowired
    private ExcelExportService excelExportService; // ✅ Ajout du service Excel

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

    // ✅ EXPORT EN EXCEL (au lieu de CSV)
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportToExcel(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "tous") String type,
            @RequestParam(defaultValue = "tous") String status,
            @RequestParam(defaultValue = "all") String dateFilter
    ) {
        try {
            // Convertir les types
            ActivityLog.ActivityType activityType = null;
            if (type != null && !type.trim().isEmpty() && !type.equalsIgnoreCase("tous")) {
                try {
                    activityType = ActivityLog.ActivityType.valueOf(type.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // Type invalide, on ignore
                }
            }

            ActivityLog.ActivityStatus activityStatus = null;
            if (status != null && !status.trim().isEmpty() && !status.equalsIgnoreCase("tous")) {
                try {
                    activityStatus = ActivityLog.ActivityStatus.valueOf(status.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // Statut invalide, on ignore
                }
            }

            LocalDateTime startDate = calculateStartDate(dateFilter);

            // Récupérer TOUTES les activités sans pagination
            List<ActivityLog> activities = service.getAllForExport(search, activityType, activityStatus, startDate);

            // Vérifier s'il y a des données à exporter
            if (activities == null || activities.isEmpty()) {
                // Retourner un message ou un fichier vide
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            // ✅ Générer le fichier Excel avec couleurs et bordures
            byte[] excelFile = excelExportService.exportActivitiesToExcel(activities);

            // Préparer la réponse HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "journal-activite-" + System.currentTimeMillis() + ".xlsx");
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelFile);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Méthode utilitaire pour calculer la date de début
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

    // Méthode utilitaire pour échapper les caractères spéciaux CSV (gardée pour compatibilité future)
    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\"", "\"\"");
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