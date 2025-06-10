package com.example.login.repositories;

import com.example.login.models.Configurateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfigurateurRepository extends JpaRepository<Configurateur, String> {
    Optional<Configurateur> findByEmail(String email);
}