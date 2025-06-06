package com.example.login.Repositories;

import com.example.login.Models.TypeAbsence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeAbsenceRepository extends JpaRepository<TypeAbsence, String> {
}
