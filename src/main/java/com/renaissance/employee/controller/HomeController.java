package com.renaissance.employee.controller;

import com.renaissance.employee.exception.ResourceNotFoundException;
import com.renaissance.employee.model.Admin;
import com.renaissance.employee.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class HomeController {
//        @RequestMapping("/")
//        public String home()    {
//            return "index";
//
//        }

    @Autowired
    AdminService service;

    @GetMapping("admins")
    public ResponseEntity<List<Admin>> getAdmin()   {
        List<Admin> admins = service.getAllAdmins();
        if(admins.isEmpty())
            throw new ResourceNotFoundException("No admins are present. Please create new Admin");
            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @GetMapping("login/{login}/{password}")
    public ResponseEntity<Admin> verifyAdmin(@PathVariable("login") String login, @PathVariable("password") String password) {
            Admin adminData =  service.verifyAdmin(login, password);
            if(adminData == null)
                throw new ResourceNotFoundException("Incorrect Login or Password");
            return new ResponseEntity<>(adminData, HttpStatus.OK);
    }

    @PostMapping("login/{login}")
    public ResponseEntity<Admin> verifyAdminByUserName(@PathVariable("login") String login) {
        Admin adminData =  service.verifyAdminByUserName(login);
        if(adminData == null)
            throw new ResourceNotFoundException("Incorrect Username");
        return new ResponseEntity<>(adminData, HttpStatus.OK);
    }

    @GetMapping("login/{id}")
    public ResponseEntity<Admin> verifyAdminById(@PathVariable("id") int id)  {
            Optional<Admin> admin = service.getAdminById(id);
            if (admin.isPresent())
                return new ResponseEntity<>(admin.get(), HttpStatus.OK);
            else
                throw new ResourceNotFoundException("Admin not found for ID : " + id);
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "login")
    public ResponseEntity<Admin> addAdmin(@Valid @RequestBody Admin admin) {
            service.addAdmin(admin);
            return new ResponseEntity<>(admin, HttpStatus.CREATED);
    }
    @PutMapping("login/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable("id") int id, @RequestBody Admin admin)  {
        Admin adminData = service.getAdminById(id).orElseThrow(()->new ResourceNotFoundException("Admin not found with ID : " + id));
            adminData.setLogin(admin.getLogin());
            adminData.setPassword(admin.getPassword());
            return new ResponseEntity<>(service.addAdmin(adminData), HttpStatus.OK);
    }

    @DeleteMapping("login/{id}")
    public ResponseEntity<HttpStatus> deleteAdminById(@PathVariable("id") int id) {

            Admin adminData = service.getAdminById(id).orElseThrow(()->new ResourceNotFoundException("Admin not found with ID : " + id));
            service.deleteAdminById(adminData.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
