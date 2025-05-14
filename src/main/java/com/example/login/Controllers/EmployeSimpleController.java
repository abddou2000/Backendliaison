package com.example.login.Controllers;

import com.example.login.Models.EmployeSimple;
import com.example.login.Repositories.EmployeSimpleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@CrossOrigin(origins = "*")
public class EmployeSimpleController {

    private final EmployeSimpleRepository repository;

    public EmployeSimpleController(EmployeSimpleRepository repository) {
        this.repository = repository;
    }

    // ✅ GET all employees
    @GetMapping
    public List<EmployeSimple> getAllEmployes() {
        return repository.findAll();
    }
}
