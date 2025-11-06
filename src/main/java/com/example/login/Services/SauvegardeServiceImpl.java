package com.example.login.Services;

import com.example.login.Models.Administrateur;
import com.example.login.Models.SauvegardeBDD;
import com.example.login.Repositories.SauvegardeBDDRepository;
import com.example.login.Services.SauvegardeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class SauvegardeServiceImpl implements SauvegardeService {

    @Autowired
    private SauvegardeBDDRepository sauvegardeRepository;

    @Value("${backup.directory:./backups}")
    private String backupDirectory;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    // --- Méthodes pour le tableau de bord et l'affichage ---

    @Override
    public List<SauvegardeBDD> findAll() {
        return sauvegardeRepository.findAll(Sort.by(Sort.Direction.DESC, "dateSauvegarde"));
    }

    @Override
    public Map<String, Object> getDashboardStats() {
        List<SauvegardeBDD> allBackups = sauvegardeRepository.findAll();

        long totalSizeOctets = allBackups.stream()
                .filter(b -> b.getTailleFichier() != null)
                .mapToLong(SauvegardeBDD::getTailleFichier)
                .sum();

        String totalSize = String.format("%.1f MB", totalSizeOctets / (1024.0 * 1024.0));

        String lastBackupDate = allBackups.stream()
                .max((b1, b2) -> b1.getDateSauvegarde().compareTo(b2.getDateSauvegarde()))
                .map(SauvegardeBDD::getDate)
                .orElse("N/A");

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalBackups", allBackups.size());
        stats.put("totalSize", totalSize);
        stats.put("lastBackup", lastBackupDate);
        stats.put("autoBackupsEnabled", true);

        return stats;
    }

    @Override
    public List<SauvegardeBDD> search(String query) {
        return sauvegardeRepository.findByNomFichierContainingIgnoreCaseOrderByDateSauvegardeDesc(query);
    }

    // --- Implémentation des actions ---

    @Override
    public SauvegardeBDD lancerSauvegardeManuelle(Administrateur admin) {
        return executerSauvegarde(SauvegardeBDD.TypeCreation.Manuel, admin);
    }

    @Override
    public void restaurerSauvegarde(String backupId) throws Exception {
        SauvegardeBDD sauvegarde = findById(backupId)
                .orElseThrow(() -> new FileNotFoundException("Sauvegarde non trouvée"));

        Path backupFile = Paths.get(sauvegarde.getEmplacement(), sauvegarde.getNomFichier());

        if (!Files.exists(backupFile)) {
            throw new FileNotFoundException("Fichier de sauvegarde introuvable : " + backupFile);
        }

        // Exécuter le fichier SQL pour restaurer
        try (Connection conn = DriverManager.getConnection(datasourceUrl, dbUsername, dbPassword);
             Statement stmt = conn.createStatement()) {

            String sqlContent = new String(Files.readAllBytes(backupFile));
            stmt.execute(sqlContent);

            System.out.println("✅ Restauration terminée avec succès");
        }
    }

    @Override
    public Resource chargerFichierEnTantQueResource(String id) throws Exception {
        SauvegardeBDD sauvegarde = findById(id)
                .orElseThrow(() -> new FileNotFoundException("Sauvegarde non trouvée avec l'id : " + id));

        try {
            Path filePath = Paths.get(sauvegarde.getEmplacement()).resolve(sauvegarde.getNomFichier()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("Fichier non trouvé ou illisible : " + sauvegarde.getNomFichier());
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("Fichier non trouvé (URL malformée) : " + sauvegarde.getNomFichier());
        }
    }

    @Override
    public void deleteByIds(List<String> ids) {
        List<SauvegardeBDD> sauvegardesASupprimer = sauvegardeRepository.findAllById(ids);
        if (!sauvegardesASupprimer.isEmpty()) {
            supprimerFichiersPhysiques(sauvegardesASupprimer);
            sauvegardeRepository.deleteAll(sauvegardesASupprimer);
        }
    }

    // --- Tâches de fond et logique interne ---

    @Override
    @Scheduled(cron = "${backup.cron:0 0 2 * * ?}")
    public void lancerSauvegardeAutomatique() {
        executerSauvegarde(SauvegardeBDD.TypeCreation.Automatique, null);
    }

    @Override
    public SauvegardeBDD executerSauvegarde(SauvegardeBDD.TypeCreation typeCreation, Administrateur admin) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String databaseName = extractDatabaseName();
        String fileName = "backup_" + databaseName + "_" + timestamp + ".sql";

        // ✅ CRÉER L'OBJET SAUVEGARDE
        SauvegardeBDD sauvegarde = new SauvegardeBDD();
        sauvegarde.setId(UUID.randomUUID().toString());
        sauvegarde.setNomFichier(fileName);
        sauvegarde.setEmplacement(backupDirectory);
        sauvegarde.setDateSauvegarde(now);
        sauvegarde.setCreatedBy(typeCreation);
        sauvegarde.setStatus(SauvegardeBDD.StatutSauvegarde.pending);
        sauvegarde.setTailleFichier(0L);

        if (admin != null) {
            sauvegarde.setCreePar(admin);
        }

        // ✅ SAUVEGARDER EN BASE IMMÉDIATEMENT
        SauvegardeBDD savedBackup = sauvegardeRepository.save(sauvegarde);

        System.out.println("✅ Sauvegarde créée en BDD avec ID: " + savedBackup.getId());

        // ✅ EXÉCUTER LA SAUVEGARDE EN ARRIÈRE-PLAN (SANS PG_DUMP)
        CompletableFuture.runAsync(() -> {
            try {
                File backupDir = new File(backupDirectory);
                if (!backupDir.exists()) {
                    backupDir.mkdirs();
                    System.out.println("✅ Dossier créé: " + backupDirectory);
                }

                Path backupFilePath = Paths.get(backupDirectory, fileName);

                // ✅ SAUVEGARDE DIRECTE AVEC JDBC (SANS PG_DUMP)
                executerSauvegardeJDBC(backupFilePath, databaseName);

                // ✅ VÉRIFIER LA TAILLE DU FICHIER
                if (Files.exists(backupFilePath)) {
                    long fileSize = Files.size(backupFilePath);
                    savedBackup.setTailleFichier(fileSize);
                    savedBackup.setStatus(SauvegardeBDD.StatutSauvegarde.success);
                    System.out.println("✅ Sauvegarde réussie: " + fileName + " (" + fileSize + " octets)");
                } else {
                    savedBackup.setStatus(SauvegardeBDD.StatutSauvegarde.failed);
                    System.err.println("❌ Fichier de sauvegarde non créé");
                }

            } catch (Exception e) {
                savedBackup.setStatus(SauvegardeBDD.StatutSauvegarde.failed);
                System.err.println("❌ Erreur: " + e.getMessage());
                e.printStackTrace();
            } finally {
                sauvegardeRepository.save(savedBackup);
            }
        });

        // ✅ RETOURNER L'OBJET IMMÉDIATEMENT
        return savedBackup;
    }

    /**
     * Sauvegarde la base de données en utilisant JDBC (sans pg_dump)
     */
    private void executerSauvegardeJDBC(Path backupFilePath, String databaseName) throws Exception {
        System.out.println("🔄 Démarrage de la sauvegarde JDBC...");

        try (Connection conn = DriverManager.getConnection(datasourceUrl, dbUsername, dbPassword);
             FileWriter writer = new FileWriter(backupFilePath.toFile())) {

            DatabaseMetaData metaData = conn.getMetaData();

            writer.write("-- Sauvegarde de la base de données: " + databaseName + "\n");
            writer.write("-- Date: " + LocalDateTime.now() + "\n\n");

            // Récupérer toutes les tables
            ResultSet tables = metaData.getTables(null, "public", "%", new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");

                // Ignorer les tables système
                if (tableName.startsWith("pg_") || tableName.startsWith("sql_")) {
                    continue;
                }

                System.out.println("📦 Sauvegarde de la table: " + tableName);

                // Structure de la table
                writer.write("\n-- Table: " + tableName + "\n");

                // Données de la table
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT * FROM \"" + tableName + "\"")) {

                    ResultSetMetaData rsMetaData = rs.getMetaData();
                    int columnCount = rsMetaData.getColumnCount();

                    while (rs.next()) {
                        StringBuilder insert = new StringBuilder("INSERT INTO \"" + tableName + "\" VALUES (");

                        for (int i = 1; i <= columnCount; i++) {
                            Object value = rs.getObject(i);

                            if (value == null) {
                                insert.append("NULL");
                            } else if (value instanceof String || value instanceof java.sql.Date ||
                                    value instanceof java.sql.Timestamp) {
                                insert.append("'").append(value.toString().replace("'", "''")).append("'");
                            } else {
                                insert.append(value);
                            }

                            if (i < columnCount) {
                                insert.append(", ");
                            }
                        }

                        insert.append(");\n");
                        writer.write(insert.toString());
                    }
                }
            }

            writer.flush();
            System.out.println("✅ Sauvegarde JDBC terminée avec succès");
        }
    }

    @Override
    public Optional<SauvegardeBDD> findById(String id) {
        return sauvegardeRepository.findById(id);
    }

    // --- Méthodes privées ---

    private void supprimerFichiersPhysiques(Iterable<SauvegardeBDD> sauvegardes) {
        for (SauvegardeBDD sauvegarde : sauvegardes) {
            if (sauvegarde.getEmplacement() == null || sauvegarde.getNomFichier() == null) continue;
            try {
                Path path = Paths.get(sauvegarde.getEmplacement(), sauvegarde.getNomFichier());
                if (Files.exists(path)) {
                    Files.delete(path);
                    System.out.println("✅ Fichier supprimé: " + path);
                }
            } catch (IOException e) {
                System.err.println("❌ Erreur: Impossible de supprimer " + sauvegarde.getNomFichier());
            }
        }
    }

    private String extractDatabaseName() {
        try {
            String urlPart = datasourceUrl.substring(datasourceUrl.indexOf("://") + 3);
            String dbNameWithParams = urlPart.substring(urlPart.indexOf("/") + 1);
            return dbNameWithParams.split("\\?")[0];
        } catch (Exception e) {
            throw new RuntimeException("❌ Impossible d'extraire le nom de la BDD", e);
        }
    }
}