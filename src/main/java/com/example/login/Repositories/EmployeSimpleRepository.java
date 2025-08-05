package com.example.login.Repositories;

import com.example.login.Models.EmployeSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeSimpleRepository extends JpaRepository<EmployeSimple, Long> {
    Optional<EmployeSimple> findByCin(String cin);
}
