package com.example.login.repositories;

import com.example.login.models.TypeAbsence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeAbsenceRepository extends JpaRepository<TypeAbsence, String> {
}
