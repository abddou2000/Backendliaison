package com.example.login.Repositories;

import com.example.login.Models.CategorieSalariale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategorieSalarialeRepository extends JpaRepository<CategorieSalariale, String> {

    Optional<CategorieSalariale> findByCodeCategorie(String codeCategorie);

    Optional<CategorieSalariale> findByNomCategorie(String nomCategorie);
}
