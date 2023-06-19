package com.renaissance.employee.service;

import com.renaissance.employee.exception.ResourceNotFoundException;
import com.renaissance.employee.model.Role;
import com.renaissance.employee.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService{
    @Autowired
    RoleRepository repository;
    @Override
    public Role addRole(Role role) {
        Role roleData = repository.findRoleByName(role.getRoleName());
        if(roleData == null)
            repository.save(role);
        else
            throw new ResourceNotFoundException("Role with name " + role.getRoleName() + " already exists");
        return  role;
    }
    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = repository.findAll();
        if(roles.isEmpty())
            throw new ResourceNotFoundException("No Roles are present. Please create new Role");
        return roles;
    }
    @Override
    public Optional<Role> findByRoleId(int roleId) {
        Optional<Role> role = repository.findById(roleId);
        if(!role.isPresent())
            throw new ResourceNotFoundException("Role not found for RoleId : " + roleId);
        return role;
    }
    @Override
    public void deleteRoleById(int roleId) {
        repository.deleteById(roleId);
    }
}
