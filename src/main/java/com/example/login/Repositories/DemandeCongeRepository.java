package com.example.login.Repositories;

import com.example.login.Models.DemandeConge;
import com.example.login.Models.DemandeConge.StatutDemandeConge;
import com.example.login.Models.DemandeConge.TypeConge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeCongeRepository extends JpaRepository<DemandeConge, Long> {

    // 🔹 Toutes les demandes d’un employé (EmployeSimple.@Id = Long id)
    List<DemandeConge> findByEmploye_Id(Long idEmploye);

    // 🔹 Par type de congé (CONGE_PAYE, JOURS_RECUPERATION, etc.)
    List<DemandeConge> findByTypeConge(TypeConge typeConge);

    // 🔹 Par statut (EN_ATTENTE, VALIDEE, REFUSEE, ANNULEE)
    List<DemandeConge> findByStatut(StatutDemandeConge statut);
}
