package com.renaissance.employee.controller;

import com.renaissance.employee.model.Role;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class AuthenticationResponse {
    private String userName;
    private String email;
    private String token;
    private Role role;

    public AuthenticationResponse(String userName, String email, String token, Role role) {
        this.userName = userName;
        this.email = email;
        this.token = token;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
