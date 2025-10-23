package com.example.login.Controllers;

import com.example.login.Models.UniteOrganisationnelle;
import com.example.login.Services.UniteOrganisationnelleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unites")
@CrossOrigin(origins = "*")
@Tag(name = "Unité Organisationnelle", description = "Unité organisationnelle management APIs")
public class UniteOrganisationnelleController {

    private final UniteOrganisationnelleService uniteService;

    @Autowired
    public UniteOrganisationnelleController(UniteOrganisationnelleService uniteService) {
        this.uniteService = uniteService;
    }

    @Operation(summary = "Create a new unite organisationnelle", description = "Creates a new unite with the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Unite created successfully",
                    content = @Content(schema = @Schema(implementation = UniteOrganisationnelle.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<UniteOrganisationnelle> createUnite(@RequestBody UniteOrganisationnelle unite) {
        UniteOrganisationnelle created = uniteService.createUnite(unite);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Get all unites", description = "Returns a list of all unites organisationnelles")
    @ApiResponse(responseCode = "200", description = "List of all unites retrieved",
            content = @Content(schema = @Schema(implementation = UniteOrganisationnelle.class)))
    @GetMapping
    public ResponseEntity<List<UniteOrganisationnelle>> getAllUnites() {
        List<UniteOrganisationnelle> unites = uniteService.listUnites();
        return ResponseEntity.ok(unites);
    }

    @Operation(summary = "Get unite by ID", description = "Returns an unite based on the ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the unite",
                    content = @Content(schema = @Schema(implementation = UniteOrganisationnelle.class))),
            @ApiResponse(responseCode = "404", description = "Unite not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UniteOrganisationnelle> getUniteById(
            @Parameter(description = "ID of the unite to retrieve") @PathVariable String id) {
        UniteOrganisationnelle unite = uniteService.getUniteById(id);
        if (unite == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(unite);
    }

    @Operation(summary = "Get unite by code", description = "Returns an unite based on the code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the unite"),
            @ApiResponse(responseCode = "404", description = "Unite not found")
    })
    @GetMapping("/search/code")
    public ResponseEntity<UniteOrganisationnelle> getUniteByCode(
            @Parameter(description = "Code of the unite") @RequestParam String codeUnite) {
        UniteOrganisationnelle unite = uniteService.findByCode(codeUnite);
        if (unite == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(unite);
    }

    @Operation(summary = "Get unite by name", description = "Returns an unite based on the name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the unite"),
            @ApiResponse(responseCode = "404", description = "Unite not found")
    })
    @GetMapping("/search/nom")
    public ResponseEntity<UniteOrganisationnelle> getUniteByNom(
            @Parameter(description = "Name of the unite") @RequestParam String nomUnite) {
        UniteOrganisationnelle unite = uniteService.findByNom(nomUnite);
        if (unite == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(unite);
    }

    @Operation(summary = "Get unites by type", description = "Returns a list of unites by type")
    @ApiResponse(responseCode = "200", description = "List of unites by type")
    @GetMapping("/search/type")
    public ResponseEntity<List<UniteOrganisationnelle>> getUnitesByType(
            @Parameter(description = "Type of unite") @RequestParam String typeUnite) {
        List<UniteOrganisationnelle> unites = uniteService.findByType(typeUnite);
        return ResponseEntity.ok(unites);
    }

    @Operation(summary = "Get unites by societe", description = "Returns a list of unites belonging to a societe")
    @ApiResponse(responseCode = "200", description = "List of unites by societe")
    @GetMapping("/search/societe")
    public ResponseEntity<List<UniteOrganisationnelle>> getUnitesBySociete(
            @Parameter(description = "ID of the societe") @RequestParam String idSociete) {
        List<UniteOrganisationnelle> unites = uniteService.findBySociete(idSociete);
        return ResponseEntity.ok(unites);
    }

    @Operation(summary = "Get sub-units by parent", description = "Returns a list of sub-units of a parent unit")
    @ApiResponse(responseCode = "200", description = "List of sub-units")
    @GetMapping("/search/parent")
    public ResponseEntity<List<UniteOrganisationnelle>> getUnitesByParent(
            @Parameter(description = "ID of the parent unite") @RequestParam String idUniteParent) {
        List<UniteOrganisationnelle> unites = uniteService.findByParent(idUniteParent);
        return ResponseEntity.ok(unites);
    }

    @Operation(summary = "Update an unite", description = "Updates an existing unite with the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unite updated successfully"),
            @ApiResponse(responseCode = "404", description = "Unite not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UniteOrganisationnelle> updateUnite(
            @Parameter(description = "ID of the unite to update") @PathVariable String id,
            @RequestBody UniteOrganisationnelle uniteDetails) {
        UniteOrganisationnelle updated = uniteService.updateUnite(id, uniteDetails);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete an unite", description = "Deletes an unite with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Unite deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Unite not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnite(
            @Parameter(description = "ID of the unite to delete") @PathVariable String id) {
        boolean deleted = uniteService.deleteUnite(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}