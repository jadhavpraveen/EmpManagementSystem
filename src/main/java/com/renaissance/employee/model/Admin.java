package com.renaissance.employee.model;

import jakarta.persistence.*;

@Entity
@Table(name="admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Admin() {

    }
    public Admin(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    @Column(name="login")
    String login;

    @Column(name="Password")
    String password;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
