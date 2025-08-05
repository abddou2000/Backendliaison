package com.example.login.Services;

import com.example.login.Models.EmployeSimple;

import java.util.List;
import java.util.Optional;

public interface EmployeSimpleService {
    Optional<EmployeSimple> getById(Long id);

    // On renomme "hire" en "create" pour correspondre à l'implémentation
    EmployeSimple create(EmployeSimple employe);

    List<EmployeSimple> getAll();
}