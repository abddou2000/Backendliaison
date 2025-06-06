package com.example.login.Repositories;

import com.example.login.Models.ParametresSysteme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametresSystemeRepository extends JpaRepository<ParametresSysteme, String> {
}
