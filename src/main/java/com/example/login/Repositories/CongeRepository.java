package com.example.login.Repositories;

import com.example.login.Models.Conge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CongeRepository extends JpaRepository<Conge, String> {
    List<Conge> findByEmploye_Id(Long id);
}