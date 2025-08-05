package com.example.login.Services;

import com.example.login.Models.Role;
import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role getById(Long id);
    Role getByType(String type);
}
