package com.renaissance.employee.controller;

import com.renaissance.employee.exception.ResourceNotFoundException;
import com.renaissance.employee.model.City;
import com.renaissance.employee.model.Employee;
import com.renaissance.employee.model.Role;
import com.renaissance.employee.service.EmployeeService;
import com.renaissance.employee.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/api")
@RequestMapping("/api/v1/auth")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    RoleService roleService;
    @GetMapping("employees")
    public ResponseEntity<List<Employee>> getEmployees()   {
        List<Employee> employees = employeeService.getAllEmployees();
        if(employees.isEmpty())
            throw new ResourceNotFoundException("No Employees present. Please create new Employee");
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    @PostMapping(value = "employees/register")
    public ResponseEntity<AuthenticationResponse> registerEmployee(@Valid @RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.registerEmployee(employee), HttpStatus.CREATED);
    }
    @PostMapping(value = "employees/signIn")
    public ResponseEntity<AuthenticationResponse> authenticateEmployee(@Valid @RequestBody AuthenticationRequest request) {
        return new ResponseEntity<>(employeeService.authenticateEmployee(request), HttpStatus.CREATED);
    }

    @GetMapping("employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") int employeeId) {
        Optional<Employee> employee = employeeService.findEmployeeById(employeeId);
        if(employee.isPresent())
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        else
            throw new ResourceNotFoundException("Employee not found for EmployeeId : " + employeeId);
    }
    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") int id, @RequestBody Employee employee)  {
        return new ResponseEntity<>(employeeService.updateEmployee(id, employee), HttpStatus.OK);
    }
    @DeleteMapping("employees/{id}")
    public ResponseEntity<HttpStatus> deleteEmployeeById(@PathVariable("id") int id) {
        Employee employee = employeeService.findEmployeeById(id).orElseThrow(()->
                new ResourceNotFoundException("Employee not found for ID : " + id));
        employeeService.deleteEmployeeById(employee.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("employees/role/{roleId}")
    public ResponseEntity<List<Employee>> getEmployeeByRoleId(@PathVariable(value = "roleId") int roleId)   {
        List<Employee> employees = employeeService.findEmployeesByRoleId(roleId);
        if(employees.isEmpty())
            throw new ResourceNotFoundException("No Employees are present for RoleID :" + roleId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    @DeleteMapping("employees/role/{id}")
    public ResponseEntity<HttpStatus> deleteEmployeeByRoleId(@PathVariable("id") int id) {
        employeeService.deleteEmployeeByRoleId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
