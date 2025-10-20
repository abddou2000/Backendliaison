package com.example.login.Controllers;

import com.example.login.Models.EmployeSimple;
import com.example.login.Services.EmployeSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employes")
@CrossOrigin(origins = "*")
public class EmployeSimpleController {

    @Autowired
    private EmployeSimpleService employeSimpleService;

    /**
     * Récupérer tous les employés avec pagination
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('RH')")
    public ResponseEntity<Page<EmployeSimple>> getAllEmployes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        try {
            Sort sort = sortDirection.equalsIgnoreCase("desc")
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<EmployeSimple> employes = employeSimpleService.findAll(pageable);

            return ResponseEntity.ok(employes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupérer tous les employés (sans pagination)
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RH')")
    public ResponseEntity<List<EmployeSimple>> getAllEmployesList() {
        try {
            List<EmployeSimple> employes = employeSimpleService.findAll();
            return ResponseEntity.ok(employes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupérer un employé par ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RH') or (hasRole('EMPLOYE') and #id == authentication.principal.id)")
    public ResponseEntity<EmployeSimple> getEmployeById(@PathVariable Long id) {
        try {
            Optional<EmployeSimple> employe = employeSimpleService.findById(id);
            return employe.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupérer un employé par matricule
     * ✅ Le matricule est maintenant dans Utilisateur
     */
    @GetMapping("/matricule/{matricule}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RH')")
    public ResponseEntity<EmployeSimple> getEmployeByMatricule(@PathVariable String matricule) {
        try {
            Optional<EmployeSimple> employe = employeSimpleService.findByMatricule(matricule);
            return employe.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Créer un nouvel employé
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('RH')")
    public ResponseEntity<EmployeSimple> createEmploye(@RequestBody EmployeSimple employe) {
        try {
            // Vérifier que l'utilisateur existe
            if (employe.getUtilisateur() == null || employe.getUtilisateur().getId() == null) {
                return ResponseEntity.badRequest().build();
            }

            EmployeSimple savedEmploye = employeSimpleService.save(employe);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmploye);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * ✅ Mettre à jour un employé
     * Le matricule est maintenant géré au niveau de l'Utilisateur
     * Grâce à cascade, la modification de l'utilisateur sera sauvegardée automatiquement
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RH')")
    public ResponseEntity<EmployeSimple> updateEmploye(@PathVariable Long id, @RequestBody EmployeSimple employeDetails) {
        try {
            Optional<EmployeSimple> optionalEmploye = employeSimpleService.findById(id);

            if (optionalEmploye.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            EmployeSimple employe = optionalEmploye.get();

            // ✅ Mise à jour du matricule au niveau de l'Utilisateur (si fourni)
            if (employeDetails.getUtilisateur() != null && employeDetails.getUtilisateur().getMatricule() != null) {
                employe.getUtilisateur().setMatricule(employeDetails.getUtilisateur().getMatricule());
            }

            // Mise à jour des autres champs de l'employé
            if (employeDetails.getAdresse() != null) {
                employe.setAdresse(employeDetails.getAdresse());
            }
            if (employeDetails.getTelephone() != null) {
                employe.setTelephone(employeDetails.getTelephone());
            }
            if (employeDetails.getPosteOccupe() != null) {
                employe.setPosteOccupe(employeDetails.getPosteOccupe());
            }
            if (employeDetails.getDateNaissance() != null) {
                employe.setDateNaissance(employeDetails.getDateNaissance());
            }
            if (employeDetails.getGenre() != null) {
                employe.setGenre(employeDetails.getGenre());
            }
            if (employeDetails.getSituationFamiliale() != null) {
                employe.setSituationFamiliale(employeDetails.getSituationFamiliale());
            }
            if (employeDetails.getEnfantsACharge() != null) {
                employe.setEnfantsACharge(employeDetails.getEnfantsACharge());
            }
            if (employeDetails.getDepartement() != null) {
                employe.setDepartement(employeDetails.getDepartement());
            }
            if (employeDetails.getTypeContrat() != null) {
                employe.setTypeContrat(employeDetails.getTypeContrat());
            }
            if (employeDetails.getUniteOrganisationnelle() != null) {
                employe.setUniteOrganisationnelle(employeDetails.getUniteOrganisationnelle());
            }

            // ✅ La sauvegarde de l'employé sauvegarde aussi l'utilisateur grâce au CascadeType.ALL
            EmployeSimple updatedEmploye = employeSimpleService.save(employe);
            return ResponseEntity.ok(updatedEmploye);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Supprimer un employé
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmploye(@PathVariable Long id) {
        try {
            if (!employeSimpleService.existsById(id)) {
                return ResponseEntity.notFound().build();
            }

            employeSimpleService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Rechercher des employés par département
     */
    @GetMapping("/departement/{departement}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RH')")
    public ResponseEntity<List<EmployeSimple>> getEmployesByDepartement(@PathVariable String departement) {
        try {
            List<EmployeSimple> employes = employeSimpleService.findByDepartement(departement);
            return ResponseEntity.ok(employes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Rechercher des employés par poste
     */
    @GetMapping("/poste/{poste}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RH')")
    public ResponseEntity<List<EmployeSimple>> getEmployesByPoste(@PathVariable String poste) {
        try {
            List<EmployeSimple> employes = employeSimpleService.findByPosteOccupe(poste);
            return ResponseEntity.ok(employes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtenir le profil de l'employé connecté
     */
    @GetMapping("/mon-profil")
    @PreAuthorize("hasRole('EMPLOYE')")
    public ResponseEntity<EmployeSimple> getMonProfil(@RequestParam Long userId) {
        try {
            Optional<EmployeSimple> employe = employeSimpleService.findById(userId);
            return employe.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}