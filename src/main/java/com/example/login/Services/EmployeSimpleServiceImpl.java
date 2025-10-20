package com.example.login.Services;

import com.example.login.Models.EmployeSimple;
import com.example.login.Repositories.EmployeSimpleRepository;
import com.example.login.Services.EmployeSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeSimpleServiceImpl implements EmployeSimpleService {

    @Autowired
    private EmployeSimpleRepository employeSimpleRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeSimple> findAll(Pageable pageable) {
        return employeSimpleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeSimple> findAll() {
        return employeSimpleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeSimple> findById(Long id) {
        return employeSimpleRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeSimple> findByMatricule(String matricule) {
        // ✅ Le matricule est maintenant dans Utilisateur
        return employeSimpleRepository.findByUtilisateurMatricule(matricule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeSimple> findByDepartement(String departement) {
        return employeSimpleRepository.findByDepartement(departement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeSimple> findByPosteOccupe(String posteOccupe) {
        return employeSimpleRepository.findByPosteOccupe(posteOccupe);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeSimple> findByTypeContrat(String typeContrat) {
        return employeSimpleRepository.findByTypeContrat(typeContrat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeSimple> findByGenre(EmployeSimple.Genre genre) {
        return employeSimpleRepository.findByGenre(genre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeSimple> findBySituationFamiliale(String situationFamiliale) {
        return employeSimpleRepository.findBySituationFamiliale(situationFamiliale);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeSimple> findByDateNaissanceBetween(LocalDate dateDebut, LocalDate dateFin) {
        return employeSimpleRepository.findByDateNaissanceBetween(dateDebut, dateFin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeSimple> findByEnfantsACharge(Integer enfantsACharge) {
        return employeSimpleRepository.findByEnfantsACharge(enfantsACharge);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeSimple> findByNomOrPrenom(String terme) {
        return employeSimpleRepository.findByUtilisateurNomContainingIgnoreCaseOrUtilisateurPrenomContainingIgnoreCase(terme, terme);
    }

    @Override
    public EmployeSimple save(EmployeSimple employe) {
        // Validation métier avant sauvegarde
        validateEmploye(employe);
        return employeSimpleRepository.save(employe);
    }

    @Override
    public void deleteById(Long id) {
        if (!employeSimpleRepository.existsById(id)) {
            throw new RuntimeException("Employé avec l'ID " + id + " n'existe pas");
        }
        employeSimpleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return employeSimpleRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByMatricule(String matricule) {
        // ✅ Le matricule est maintenant dans Utilisateur
        return employeSimpleRepository.existsByUtilisateurMatricule(matricule);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return employeSimpleRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long countByDepartement(String departement) {
        return employeSimpleRepository.countByDepartement(departement);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByTypeContrat(String typeContrat) {
        return employeSimpleRepository.countByTypeContrat(typeContrat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findDistinctDepartements() {
        return employeSimpleRepository.findDistinctDepartements();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findDistinctPostes() {
        return employeSimpleRepository.findDistinctPostes();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findDistinctTypesContrat() {
        return employeSimpleRepository.findDistinctTypesContrat();
    }

    /**
     * ✅ Validation métier d'un employé (matricule supprimé car maintenant dans Utilisateur)
     */
    private void validateEmploye(EmployeSimple employe) {
        if (employe == null) {
            throw new IllegalArgumentException("L'employé ne peut pas être null");
        }

        if (employe.getUtilisateur() == null) {
            throw new IllegalArgumentException("L'utilisateur associé est obligatoire");
        }

        // Validation de la date de naissance
        if (employe.getDateNaissance() != null && employe.getDateNaissance().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de naissance ne peut pas être dans le futur");
        }

        // Validation du nombre d'enfants à charge
        if (employe.getEnfantsACharge() != null && employe.getEnfantsACharge() < 0) {
            throw new IllegalArgumentException("Le nombre d'enfants à charge ne peut pas être négatif");
        }

        // Validation du numéro de téléphone (format basique)
        if (employe.getTelephone() != null && !employe.getTelephone().trim().isEmpty()) {
            String telephone = employe.getTelephone().replaceAll("[\\s\\-\\.]", "");
            if (!telephone.matches("^(\\+212|0)[0-9]{9}$")) {
                throw new IllegalArgumentException("Format de téléphone invalide (format attendu: +212XXXXXXXXX ou 0XXXXXXXXX)");
            }
        }
    }

    /**
     * Méthodes utilitaires additionnelles
     */

    /**
     * Rechercher des employés par terme général (nom, prénom, matricule, département, poste)
     */
    @Transactional(readOnly = true)
    public List<EmployeSimple> rechercherEmployes(String terme) {
        if (terme == null || terme.trim().isEmpty()) {
            return findAll();
        }
        return employeSimpleRepository.rechercherParTermeGeneral(terme.trim());
    }

    /**
     * Obtenir les employés d'anniversaire aujourd'hui
     */
    @Transactional(readOnly = true)
    public List<EmployeSimple> getEmployesAnniversaireAujourdhui() {
        LocalDate aujourdhui = LocalDate.now();
        return employeSimpleRepository.findByDateNaissanceMonthAndDateNaissanceDay(
                aujourdhui.getMonthValue(),
                aujourdhui.getDayOfMonth()
        );
    }

    /**
     * Obtenir les employés par tranche d'âge
     */
    @Transactional(readOnly = true)
    public List<EmployeSimple> getEmployesParTrancheAge(int ageMin, int ageMax) {
        LocalDate dateMax = LocalDate.now().minusYears(ageMin);
        LocalDate dateMin = LocalDate.now().minusYears(ageMax + 1);
        return findByDateNaissanceBetween(dateMin, dateMax);
    }

    /**
     * Mettre à jour les informations de contact d'un employé
     */
    public EmployeSimple updateContactInfo(Long id, String adresse, String telephone) {
        Optional<EmployeSimple> optionalEmploye = findById(id);
        if (optionalEmploye.isEmpty()) {
            throw new RuntimeException("Employé avec l'ID " + id + " n'existe pas");
        }

        EmployeSimple employe = optionalEmploye.get();
        if (adresse != null) {
            employe.setAdresse(adresse);
        }
        if (telephone != null) {
            employe.setTelephone(telephone);
        }

        return save(employe);
    }
}