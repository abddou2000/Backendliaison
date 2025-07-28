package com.example.login.Services;

import com.example.login.Models.Role;
import com.example.login.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    /** Crée ou met à jour un rôle */
    public Role saveRole(Role role) {
        return repository.save(role);
    }

    /** Retourne tous les rôles */
    public List<Role> getAllRoles() {
        return repository.findAll();
    }

    /** Retourne un rôle par son ID */
    public Optional<Role> getRoleById(String idRole) {
        return repository.findByIdRole(idRole);
    }

    /** Supprime un rôle par son ID */
    public void deleteRole(String idRole) {
        repository.deleteById(idRole);
    }
}
