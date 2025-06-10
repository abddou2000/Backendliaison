package com.example.login.Services;

import com.example.login.models.TypeContrat;
import com.example.login.repositories.TypeContratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TypeContratService {

    private final TypeContratRepository typeContratRepository;

    @Autowired
    public TypeContratService(TypeContratRepository typeContratRepository) {
        this.typeContratRepository = typeContratRepository;
    }

    /**
     * Crée un nouveau type de contrat
     */
    public TypeContrat create(TypeContrat typeContrat) {
        return typeContratRepository.save(typeContrat);
    }

    /**
     * Retourne la liste de tous les types de contrat
     */
    public List<TypeContrat> listAll() {
        return typeContratRepository.findAll();
    }

    /**
     * Recherche un type de contrat par son code
     */
    public Optional<TypeContrat> findByCode(String code) {
        return typeContratRepository.findById(code);
    }

    /**
     * Recherche un type de contrat par son nom
     */
    public Optional<TypeContrat> findByNom(String nomContrat) {
        return typeContratRepository.findByNomContrat(nomContrat);
    }

    /**
     * Met à jour un type de contrat existant
     */
    @Transactional
    public Optional<TypeContrat> update(String code, TypeContrat details) {
        return typeContratRepository.findById(code)
                .map(existing -> {
                    existing.setNomContrat(details.getNomContrat());
                    existing.setPeriodeEssai(details.getPeriodeEssai());
                    existing.setDateDebut(details.getDateDebut());
                    existing.setDateFin(details.getDateFin());
                    existing.setConditionsSpecifiques(details.getConditionsSpecifiques());
                    return existing;
                });
    }

    /**
     * Supprime un type de contrat par son code
     */
    @Transactional
    public boolean delete(String code) {
        if (typeContratRepository.existsById(code)) {
            typeContratRepository.deleteById(code);
            return true;
        }
        return false;
    }
}
