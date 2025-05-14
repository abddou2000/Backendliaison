package com.example.login.Controllers;

import com.example.login.Models.cotisation;
import com.example.login.Services.CotisationService;
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
@RequestMapping("/api/cotisations")
@CrossOrigin(origins = "*")
@Tag(name = "Cotisation", description = "Cotisation management APIs")
public class CotisationController {

    private final CotisationService cotisationService;

    @Autowired
    public CotisationController(CotisationService cotisationService) {
        this.cotisationService = cotisationService;
    }

    @Operation(summary = "Create a new cotisation", description = "Creates a new cotisation with the provided data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cotisation created successfully", 
                    content = @Content(schema = @Schema(implementation = cotisation.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<cotisation> createCotisation(@RequestBody cotisation cotisation) {
        cotisation created = cotisationService.createCotisation(cotisation);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update a cotisation", description = "Updates an existing cotisation with the provided ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cotisation updated successfully"),
        @ApiResponse(responseCode = "404", description = "Cotisation not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<cotisation> updateCotisation(
            @Parameter(description = "ID of the cotisation to update") @PathVariable String id,
            @RequestBody cotisation cotisationDetails) {
        
        cotisation updated = cotisationService.updateCotisation(id, cotisationDetails);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete a cotisation", description = "Deletes a cotisation with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cotisation deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Cotisation not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCotisation(
            @Parameter(description = "ID of the cotisation to delete") @PathVariable String id) {
        boolean deleted = cotisationService.deleteCotisation(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get cotisation by ID", description = "Returns a cotisation based on the ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the cotisation",
                    content = @Content(schema = @Schema(implementation = cotisation.class))),
        @ApiResponse(responseCode = "404", description = "Cotisation not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<cotisation> getCotisationById(
            @Parameter(description = "ID of the cotisation to retrieve") @PathVariable String id) {
        cotisation cotisation = cotisationService.getCotisationById(id);
        if (cotisation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cotisation);
    }

    @Operation(summary = "Get all cotisations", description = "Returns a list of all cotisations")
    @ApiResponse(responseCode = "200", description = "List of all cotisations retrieved",
                content = @Content(schema = @Schema(implementation = cotisation.class)))
    @GetMapping
    public ResponseEntity<List<cotisation>> listCotisations() {
        List<cotisation> cotisations = cotisationService.listCotisations();
        return ResponseEntity.ok(cotisations);
    }
}