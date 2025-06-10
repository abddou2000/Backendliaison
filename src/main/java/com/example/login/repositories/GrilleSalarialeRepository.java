package com.example.login.repositories;

import com.example.login.models.GrilleSalariale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrilleSalarialeRepository extends JpaRepository<GrilleSalariale, String> {
}
