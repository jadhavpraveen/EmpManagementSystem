package com.renaissance.employee.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="employee_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private int id;

    @Column(name="role_name")
    @NotBlank(message = "Role name is required")
    @Size(min=3, max= 50, message = "Role name must be from 3 to 50 characters")
    private String roleName;

    public Role(int roleId, String roleName) {
        this.id = roleId;
        this.roleName = roleName;
    }
    public Role()   {

    }
    public int getRoleId() {
        return id;
    }
    public void setRoleId(int roleId) {
        this.id = roleId;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
