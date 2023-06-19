package com.renaissance.employee.service;

import com.renaissance.employee.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    public Role addRole(Role role);
    public List<Role> getAllRoles();
    public Optional<Role> findByRoleId(int roleId);
    public void deleteRoleById(int roleId);
}
