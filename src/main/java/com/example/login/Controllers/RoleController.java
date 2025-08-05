package com.example.login.Controllers;

import com.example.login.Models.Role;
import com.example.login.Services.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService service;

    public RoleController(RoleService Service) {
        this.service = Service;
    }

    @GetMapping
    public List<Role> listAll() {
        return service.getAllRoles();
    }

    @GetMapping("/{id}")
    public Role getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/type/{type}")
    public Role getByType(@PathVariable String type) {
        return service.getByType(type);
    }
}
