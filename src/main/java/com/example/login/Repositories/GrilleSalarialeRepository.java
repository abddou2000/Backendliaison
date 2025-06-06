package com.example.login.Repositories;

import com.example.login.Models.GrilleSalariale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrilleSalarialeRepository extends JpaRepository<GrilleSalariale, String> {
}
