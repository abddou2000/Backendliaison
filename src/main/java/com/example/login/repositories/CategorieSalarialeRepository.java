package com.example.login.repositories;

import com.example.login.models.CategorieSalariale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategorieSalarialeRepository extends JpaRepository<CategorieSalariale, String> {

    Optional<CategorieSalariale> findByCodeCategorie(String codeCategorie);

    Optional<CategorieSalariale> findByNomCategorie(String nomCategorie);
}
