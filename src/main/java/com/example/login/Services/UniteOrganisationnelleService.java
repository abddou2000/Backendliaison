package com.example.login.Services;

import com.example.login.Models.UniteOrganisationnelle;
import com.example.login.Repositories.UniteOrganisationnelleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UniteOrganisationnelleService {

    private final UniteOrganisationnelleRepository uniteRepository;

    @Autowired
    public UniteOrganisationnelleService(UniteOrganisationnelleRepository uniteRepository) {
        this.uniteRepository = uniteRepository;
    }

    /**
     * Create a new unite organisationnelle
     * @param unite Unite data
     * @return The created unite
     */
    @Transactional
    public UniteOrganisationnelle createUnite(UniteOrganisationnelle unite) {
        if (unite.getIdUnite() == null || unite.getIdUnite().trim().isEmpty()) {
            unite.setIdUnite(UUID.randomUUID().toString());
        }
        return uniteRepository.save(unite);
    }

    /**
     * Update an existing unite organisationnelle
     * @param id Unite ID
     * @param uniteDetails Updated unite data
     * @return The updated unite
     */
    @Transactional
    public UniteOrganisationnelle updateUnite(String id, UniteOrganisationnelle uniteDetails) {
        UniteOrganisationnelle existingUnite = getUniteById(id);

        if (existingUnite == null) {
            return null;
        }

        existingUnite.setCodeUnite(uniteDetails.getCodeUnite());
        existingUnite.setNomUnite(uniteDetails.getNomUnite());
        existingUnite.setTypeUnite(uniteDetails.getTypeUnite());
        existingUnite.setUniteParent(uniteDetails.getUniteParent());
        existingUnite.setSociete(uniteDetails.getSociete());
        existingUnite.setDescriptionUnite(uniteDetails.getDescriptionUnite());
        existingUnite.setDateDebut(uniteDetails.getDateDebut());
        existingUnite.setDateFin(uniteDetails.getDateFin());

        return uniteRepository.save(existingUnite);
    }

    /**
     * Delete an unite organisationnelle
     * @param id Unite ID
     * @return true if deleted successfully, false otherwise
     */
    @Transactional
    public boolean deleteUnite(String id) {
        if (uniteRepository.existsById(id)) {
            uniteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Get an unite organisationnelle by ID
     * @param id Unite ID
     * @return The unite if found, null otherwise
     */
    public UniteOrganisationnelle getUniteById(String id) {
        Optional<UniteOrganisationnelle> unite = uniteRepository.findById(id);
        return unite.orElse(null);
    }

    /**
     * List all unites organisationnelles
     * @return List of all unites
     */
    public List<UniteOrganisationnelle> listUnites() {
        return uniteRepository.findAll();
    }

    /**
     * Find unite by code
     * @param codeUnite Code of the unite
     * @return The unite if found, null otherwise
     */
    public UniteOrganisationnelle findByCode(String codeUnite) {
        Optional<UniteOrganisationnelle> unite = uniteRepository.findByCodeUnite(codeUnite);
        return unite.orElse(null);
    }

    /**
     * Find unite by name
     * @param nomUnite Name of the unite
     * @return The unite if found, null otherwise
     */
    public UniteOrganisationnelle findByNom(String nomUnite) {
        Optional<UniteOrganisationnelle> unite = uniteRepository.findByNomUnite(nomUnite);
        return unite.orElse(null);
    }

    /**
     * Find unites by type
     * @param typeUnite Type of unite
     * @return List of unites matching the type
     */
    public List<UniteOrganisationnelle> findByType(String typeUnite) {
        return uniteRepository.findByTypeUnite(typeUnite);
    }

    /**
     * Find unites by societe
     * @param idSociete Societe ID
     * @return List of unites belonging to the societe
     */
    public List<UniteOrganisationnelle> findBySociete(String idSociete) {
        return uniteRepository.findBySociete_IdSociete(idSociete);
    }

    /**
     * Find sub-units of a parent unit
     * @param idUniteParent Parent unit ID
     * @return List of sub-units
     */
    public List<UniteOrganisationnelle> findByParent(String idUniteParent) {
        return uniteRepository.findByUniteParent_IdUnite(idUniteParent);
    }
}