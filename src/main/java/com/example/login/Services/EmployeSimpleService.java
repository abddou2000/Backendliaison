package com.example.login.Services;

import com.example.login.Models.EmployeSimple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeSimpleService {

    Page<EmployeSimple> findAll(Pageable pageable);

    List<EmployeSimple> findAll();

    Optional<EmployeSimple> findById(Long id);

    Optional<EmployeSimple> findByMatricule(String matricule);

    Optional<EmployeSimple> findByUtilisateurId(Long utilisateurId);

    EmployeSimple save(EmployeSimple employe);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByUtilisateurId(Long utilisateurId);

    List<EmployeSimple> findByDepartement(String departement);

    List<EmployeSimple> findByPosteOccupe(String posteOccupe);
}