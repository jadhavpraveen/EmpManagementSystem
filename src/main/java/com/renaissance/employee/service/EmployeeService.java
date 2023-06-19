package com.renaissance.employee.service;

import com.renaissance.employee.controller.AuthenticationRequest;
import com.renaissance.employee.controller.AuthenticationResponse;
import com.renaissance.employee.exception.ResourceNotFoundException;
import com.renaissance.employee.model.Employee;
import com.renaissance.employee.model.Role;
import com.renaissance.employee.repository.EmployeeRepository;
import com.renaissance.employee.repository.RoleRepository;
import com.renaissance.employee.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService{

    @Autowired
    EmployeeRepository repository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
//    @Autowired
//    AuthenticationResponse authenticationResponse;

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = repository.findAll();
        return employees;
    }

    @Override
    public AuthenticationResponse registerEmployee(Employee employee) {
        Role role = roleRepository.findRoleByName(employee.getRole().getRoleName());
        if(role == null)
            throw new ResourceNotFoundException("Not found Role with name  " + employee.getRole().getRoleName());
        if(repository.existsByUserName(employee.getUserName()))
            throw new ResourceNotFoundException("UserName " + employee.getUserName() + " already exists");
        else if(repository.existsByEmail(employee.getEmail()))
            throw new ResourceNotFoundException("Email " + employee.getEmail() + " already exists");

        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setRole(role);
        repository.save(employee);

       String jwtToken = jwtService.generateToken(employee);
        //return repository.save(employee);
        //return employee;
        return  new AuthenticationResponse(
                employee.getUserName(),
                employee.getEmail(),
                jwtToken,
                employee.getRole()
            );
    }
    @Override
    public AuthenticationResponse authenticateEmployee(AuthenticationRequest authenticationRequest) {
        //authenticationRequest.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUserName(),
                        authenticationRequest.getPassword()
                ));

        //SecurityContextHolder.getContext().setAuthentication((Authentication) authentication);
        Employee emp = repository.findByUserName(authenticationRequest.getUserName())
                .orElseThrow(()-> new ResourceNotFoundException("User does not exist"));
        String jwtToken = jwtService.generateToken(emp);

        return  new AuthenticationResponse(
                emp.getUserName(),
                emp.getEmail(),
                jwtToken,
                emp.getRole()
        );
    }
    @Override
    public Employee updateEmployee(long empId, Employee employee)    {
        Role role = roleRepository.findRoleByName(employee.getRole().getRoleName());
        if(role == null)
            throw new ResourceNotFoundException("Not found Role with name  " + employee.getRole().getRoleName());
        Employee employeeData = repository.findById(empId).orElseThrow(()->
                new ResourceNotFoundException("Employee not found for ID : " + empId));
        employeeData.setUserName(employee.getUserName());
        employeeData.setPassword(employee.getPassword());
        employeeData.setFirstName(employee.getFirstName());
        employeeData.setLastName(employee.getLastName());
        employeeData.setEmail(employee.getEmail());
        employeeData.setGender(employee.getGender());
        employeeData.setPhoneNumber(employee.getPhoneNumber());
        employeeData.setDesignation(employee.getDesignation());
        employeeData.setSalary(employee.getSalary());
        employeeData.setAge(employee.getAge());
        employeeData.setRole(role);

        return  repository.save(employeeData);
    }

    @Override
    public Optional<Employee> findEmployeeById(long employeeId) {
        return repository.findById(employeeId);
    }

    @Override
    public List<Employee> findEmployeesByRoleId(int roleId) {
        if(!roleRepository.existsById(roleId))
            throw new ResourceNotFoundException("No Employee exist with Role ID : " + roleId);
        return repository.findEmployeesByRoleId(roleId);
    }

    @Override
    public void deleteEmployeeById(long employeeId) {
            repository.deleteById(employeeId);
    }

    @Override
    public void deleteEmployeeByRoleId(int roleId) {
        if(!roleRepository.existsById(roleId))
            throw new ResourceNotFoundException("No Employee exist with Role ID : " + roleId);
        repository.deleteEmployeesByRoleId(roleId);
    }
}
