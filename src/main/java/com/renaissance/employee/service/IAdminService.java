package com.renaissance.employee.service;

import com.renaissance.employee.model.Admin;
import java.util.List;
import java.util.Optional;

public interface IAdminService {
     public List<Admin> getAllAdmins() ;
     public Admin verifyAdmin(String login, String password);
     public Admin verifyAdminByUserName(String login);
     public Optional<Admin> getAdminById(int id);
     public Admin addAdmin(Admin admin);
     public void deleteAdminById(int id);


}
