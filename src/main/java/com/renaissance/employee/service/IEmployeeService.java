package com.renaissance.employee.service;

import com.renaissance.employee.controller.AuthenticationRequest;
import com.renaissance.employee.controller.AuthenticationResponse;
import com.renaissance.employee.model.City;
import com.renaissance.employee.model.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    public List<Employee> getAllEmployees();
    public AuthenticationResponse registerEmployee(Employee employee);
    public AuthenticationResponse authenticateEmployee(AuthenticationRequest authenticationRequest);
    public Optional<Employee> findEmployeeById(long employeeId);
    public List<Employee> findEmployeesByRoleId(int roleId);
    public void deleteEmployeeById(long employeeId);
    public void deleteEmployeeByRoleId(int roleId);
    public Employee updateEmployee(long empId, Employee emp);

}
