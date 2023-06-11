package com.renaissance.employee.service;

import com.renaissance.employee.model.Admin;
import com.renaissance.employee.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IAdminService{

    @Autowired
    AdminRepository repo;
    @Override
    public List<Admin> getAllAdmins()
    {
        List<Admin> admin = repo.findAll();
        return admin;
    }
    @Override
    public Admin verifyAdmin(String login, String password)
    {
        Admin admin = repo.verifyAdmin(login, password);
        return admin;
    }
    @Override
    public Admin verifyAdminByUserName(String login)
    {
        Admin admin = repo.verifyAdminByUserName(login);
        return admin;
    }
    @Override
    public Optional<Admin> getAdminById(int id)
    {
       Optional<Admin> admin = repo.findById(id);
        return admin;
    }
    @Override
    public Admin addAdmin(Admin admin)
    {
        repo.save(admin);
        return admin;
    }
    @Override
    public void deleteAdminById(int id)
    {
        repo.deleteById(id);
    }
}
