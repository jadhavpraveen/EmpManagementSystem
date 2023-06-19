package com.renaissance.employee.controller;

import com.renaissance.employee.exception.ResourceNotFoundException;
import com.renaissance.employee.model.Role;
import com.renaissance.employee.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    RoleService service;
    @GetMapping("roles")
    public ResponseEntity<List<Role>> getRoles()   {
        List<Role> roles = service.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
    @PostMapping(value = "roles")
    public ResponseEntity<Role> addRole(@Valid @RequestBody Role role) {
        service.addRole(role);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }
    @GetMapping("roles/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable(value = "id") int roleId) {
        Optional<Role> role = service.findByRoleId(roleId);
        return new ResponseEntity<>(role.get(), HttpStatus.OK);
    }
    @PutMapping("roles/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable("id") int id, @RequestBody Role role)  {
        Role roleData = service.findByRoleId(id).orElseThrow(()->new ResourceNotFoundException("Role not found for ID : " + id));
        roleData.setRoleName(role.getRoleName());
        return new ResponseEntity<>(service.addRole(roleData), HttpStatus.OK);
    }
    @DeleteMapping("roles/{id}")
    public ResponseEntity<HttpStatus> deleteRoleById(@PathVariable("id") int id) {
        Role role = service.findByRoleId(id).orElseThrow(()->new ResourceNotFoundException("Role not found for ID : " + id));
        service.deleteRoleById(role.getRoleId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public static class AuthenticationResponse {
    }
}
