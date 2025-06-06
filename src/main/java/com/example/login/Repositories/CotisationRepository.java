package com.example.login.Repositories;

import com.example.login.Models.Cotisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotisationRepository extends JpaRepository<Cotisation, String> {
}