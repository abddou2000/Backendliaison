package com.example.login.Services;

import com.example.login.Models.cotisation;
import com.example.login.Repositories.CotisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CotisationService {

    private final CotisationRepository cotisationRepository;

    @Autowired
    public CotisationService(CotisationRepository cotisationRepository) {
        this.cotisationRepository = cotisationRepository;
    }

    /**
     * Create a new cotisation
     * @param cotisation Cotisation data
     * @return The created cotisation
     */
    @Transactional
    public cotisation createCotisation(cotisation cotisation) {
        if (cotisation.getIdCotisation() == null || cotisation.getIdCotisation().trim().isEmpty()) {
            cotisation.setIdCotisation(UUID.randomUUID().toString());
        }
        return cotisationRepository.save(cotisation);
    }

    /**
     * Update an existing cotisation
     * @param id Cotisation ID
     * @param cotisationDetails Updated cotisation data
     * @return The updated cotisation
     */
    @Transactional
    public cotisation updateCotisation(String id, cotisation cotisationDetails) {
        cotisation existingCotisation = getCotisationById(id);
        
        if (existingCotisation == null) {
            return null;
        }
        
        existingCotisation.setTypeCotisation(cotisationDetails.getTypeCotisation());
        existingCotisation.setTypeCotisationRef(cotisationDetails.getTypeCotisationRef());
        existingCotisation.setTauxSalarial(cotisationDetails.getTauxSalarial());
        existingCotisation.setTauxPatronal(cotisationDetails.getTauxPatronal());
        existingCotisation.setPlafondSalarial(cotisationDetails.getPlafondSalarial());
        existingCotisation.setPlafondPatronal(cotisationDetails.getPlafondPatronal());
        existingCotisation.setDateDebut(cotisationDetails.getDateDebut());
        existingCotisation.setDateFin(cotisationDetails.getDateFin());
        existingCotisation.setDescription(cotisationDetails.getDescription());
        
        return cotisationRepository.save(existingCotisation);
    }

    /**
     * Delete a cotisation
     * @param id Cotisation ID
     * @return true if deleted successfully, false otherwise
     */
    @Transactional
    public boolean deleteCotisation(String id) {
        if (cotisationRepository.existsById(id)) {
            cotisationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Get a cotisation by ID
     * @param id Cotisation ID
     * @return The cotisation if found, null otherwise
     */
    public cotisation getCotisationById(String id) {
        Optional<cotisation> cotisation = cotisationRepository.findById(id);
        return cotisation.orElse(null);
    }

    /**
     * List all cotisations
     * @return List of all cotisations
     */
    public List<cotisation> listCotisations() {
        return cotisationRepository.findAll();
    }
}