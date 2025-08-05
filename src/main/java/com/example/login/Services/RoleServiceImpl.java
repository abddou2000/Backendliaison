package com.example.login.Services;

import com.example.login.Models.Role;
import com.example.login.Repositories.RoleRepository;
import com.example.login.Services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepo;

    public RoleServiceImpl(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Role getById(Long id) {
        return roleRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rôle introuvable : " + id));
    }

    @Override
    public Role getByType(String type) {
        return roleRepo.findAll().stream()
                .filter(r -> r.getType().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Rôle introuvable : " + type));
    }
}
