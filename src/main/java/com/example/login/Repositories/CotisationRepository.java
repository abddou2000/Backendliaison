package com.example.login.Repositories;

import com.example.login.Models.cotisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotisationRepository extends JpaRepository<cotisation, String> {
}