package com.example.login.repositories;

import com.example.login.models.Cotisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotisationRepository extends JpaRepository<Cotisation, String> {
}