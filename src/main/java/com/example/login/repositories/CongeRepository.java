package com.example.login.repositories;

import com.example.login.models.Conge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CongeRepository extends JpaRepository<Conge, String> {
    List<Conge> findByEmploye_IdEmploye(String idEmploye);
}