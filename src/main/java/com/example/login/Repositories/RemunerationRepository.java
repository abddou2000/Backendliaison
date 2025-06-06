package com.example.login.Repositories;

import com.example.login.Models.Remuneration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RemunerationRepository extends JpaRepository<Remuneration, String> {
    Optional<Remuneration> findByEmploye_IdEmploye(String idEmploye);
}