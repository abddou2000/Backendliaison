package com.example.login.Services;

import com.example.login.Models.ActivityLog;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelExportService {

    public byte[] exportActivitiesToExcel(List<ActivityLog> activities) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Journal d'Activité");

        // Créer les styles
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dateStyle = createDateStyle(workbook);
        CellStyle successStyle = createSuccessStyle(workbook);
        CellStyle warningStyle = createWarningStyle(workbook);
        CellStyle errorStyle = createErrorStyle(workbook);
        CellStyle normalStyle = createNormalStyle(workbook);

        // En-tête
        Row headerRow = sheet.createRow(0);
        String[] columns = {
                "ID", "Date et Heure", "Nom Utilisateur", "Email",
                "Action", "Type", "Statut", "Cible", "Adresse IP", "User Agent"
        };

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // Format de date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        // Données
        int rowNum = 1;
        for (ActivityLog activity : activities) {
            Row row = sheet.createRow(rowNum++);

            // ID
            Cell idCell = row.createCell(0);
            idCell.setCellValue(activity.getId());
            idCell.setCellStyle(normalStyle);

            // Date et Heure
            Cell dateCell = row.createCell(1);
            dateCell.setCellValue(activity.getTimestamp() != null ?
                    activity.getTimestamp().format(formatter) : "");
            dateCell.setCellStyle(dateStyle);

            // Nom Utilisateur
            Cell nameCell = row.createCell(2);
            nameCell.setCellValue(activity.getUserName());
            nameCell.setCellStyle(normalStyle);

            // Email
            Cell emailCell = row.createCell(3);
            emailCell.setCellValue(activity.getUserEmail());
            emailCell.setCellStyle(normalStyle);

            // Action
            Cell actionCell = row.createCell(4);
            actionCell.setCellValue(activity.getAction());
            actionCell.setCellStyle(normalStyle);

            // Type
            Cell typeCell = row.createCell(5);
            typeCell.setCellValue(activity.getType() != null ?
                    formatTypeLabel(activity.getType().toString()) : "");
            typeCell.setCellStyle(getTypeStyle(activity.getType(), workbook));

            // Statut
            Cell statusCell = row.createCell(6);
            statusCell.setCellValue(activity.getStatus() != null ?
                    formatStatusLabel(activity.getStatus().toString()) : "");

            // Appliquer le style selon le statut
            if (activity.getStatus() != null) {
                switch (activity.getStatus()) {
                    case SUCCESS:
                        statusCell.setCellStyle(successStyle);
                        break;
                    case WARNING:
                        statusCell.setCellStyle(warningStyle);
                        break;
                    case ERROR:
                        statusCell.setCellStyle(errorStyle);
                        break;
                    default:
                        statusCell.setCellStyle(normalStyle);
                }
            } else {
                statusCell.setCellStyle(normalStyle);
            }

            // Cible
            Cell targetCell = row.createCell(7);
            targetCell.setCellValue(activity.getTarget() != null ? activity.getTarget() : "");
            targetCell.setCellStyle(normalStyle);

            // Adresse IP
            Cell ipCell = row.createCell(8);
            ipCell.setCellValue(activity.getIpAddress());
            ipCell.setCellStyle(normalStyle);

            // User Agent
            Cell uaCell = row.createCell(9);
            uaCell.setCellValue(activity.getUserAgent() != null ? activity.getUserAgent() : "");
            uaCell.setCellStyle(normalStyle);
        }

        // Auto-size des colonnes
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
            // Ajouter un peu d'espace supplémentaire
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
        }

        // Figer la première ligne (en-tête)
        sheet.createFreezePane(0, 1);

        // Écrire dans un ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

    // Style pour l'en-tête
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        // Couleur de fond bleu foncé
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Police blanche en gras
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);

        // Bordures
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        // Alignement
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

    // Style pour les dates
    private CellStyle createDateStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);

        // Bordures
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    // Style pour les cellules normales
    private CellStyle createNormalStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);

        // Bordures
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        style.setWrapText(false);

        return style;
    }

    // Style pour SUCCESS (vert)
    private CellStyle createSuccessStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setColor(IndexedColors.DARK_GREEN.getIndex());
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);

        // Bordures
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        style.setAlignment(HorizontalAlignment.CENTER);

        return style;
    }

    // Style pour WARNING (orange)
    private CellStyle createWarningStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setColor(IndexedColors.DARK_RED.getIndex());
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);

        // Bordures
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        style.setAlignment(HorizontalAlignment.CENTER);

        return style;
    }

    // Style pour ERROR (rouge)
    private CellStyle createErrorStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        style.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setColor(IndexedColors.DARK_RED.getIndex());
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);

        // Bordures
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        style.setAlignment(HorizontalAlignment.CENTER);

        return style;
    }

    // Style pour les types d'activité
    private CellStyle getTypeStyle(ActivityLog.ActivityType type, Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);

        if (type != null) {
            switch (type) {
                case CONNEXION:
                    style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                    font.setColor(IndexedColors.DARK_BLUE.getIndex());
                    break;
                case CREATION:
                    style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                    font.setColor(IndexedColors.DARK_GREEN.getIndex());
                    break;
                case MODIFICATION:
                    style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
                    font.setColor(IndexedColors.DARK_RED.getIndex());
                    break;
                case SUPPRESSION:
                    style.setFillForegroundColor(IndexedColors.ROSE.getIndex());
                    font.setColor(IndexedColors.DARK_RED.getIndex());
                    break;
                case EXPORT:
                    style.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
                    font.setColor(IndexedColors.DARK_BLUE.getIndex());
                    break;
                case CONFIGURATION:
                    style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                    font.setColor(IndexedColors.BLACK.getIndex());
                    break;
            }
        }

        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);

        // Bordures
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        style.setAlignment(HorizontalAlignment.CENTER);

        return style;
    }

    private String formatTypeLabel(String type) {
        switch (type) {
            case "CONNEXION": return "Connexion";
            case "CREATION": return "Création";
            case "MODIFICATION": return "Modification";
            case "SUPPRESSION": return "Suppression";
            case "EXPORT": return "Export";
            case "CONFIGURATION": return "Configuration";
            default: return type;
        }
    }

    private String formatStatusLabel(String status) {
        switch (status) {
            case "SUCCESS": return "Succès";
            case "WARNING": return "Avertissement";
            case "ERROR": return "Erreur";
            default: return status;
        }
    }
}