package com.example.login.repositories;

import com.example.login.models.EmployeSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeSimpleRepository extends JpaRepository<EmployeSimple, String> {

    @Query("SELECT e FROM EmployeSimple e JOIN FETCH e.role WHERE e.emailPro = :email")
    Optional<EmployeSimple> findByEmailProWithRole(@Param("email") String email);

    @Query("SELECT e FROM EmployeSimple e JOIN FETCH e.role WHERE e.emailPerso = :email")
    Optional<EmployeSimple> findByEmailPersoWithRole(@Param("email") String email);

    Optional<EmployeSimple> findByEmailPro(String email);
}
