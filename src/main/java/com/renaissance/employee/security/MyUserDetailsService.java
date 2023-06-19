package com.renaissance.employee.security;

import com.renaissance.employee.model.Admin;
import com.renaissance.employee.model.Employee;
import com.renaissance.employee.repository.EmployeeRepository;
import com.renaissance.employee.service.AdminService;
import com.renaissance.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyUserDetailsService {//} implements UserDetailsService {

//    @Autowired
//    //EmployeeService service;
//    EmployeeRepository repository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        Optional<Employee> employee = repository.findByUserName(username);
//        if(!employee.isPresent())
//            throw new UsernameNotFoundException("User not found");
//       //return null;
//        return new UserPrincipal(admin);
   // }
}
