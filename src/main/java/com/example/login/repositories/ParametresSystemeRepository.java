package com.example.login.repositories;

import com.example.login.models.ParametresSysteme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametresSystemeRepository extends JpaRepository<ParametresSysteme, String> {
}
