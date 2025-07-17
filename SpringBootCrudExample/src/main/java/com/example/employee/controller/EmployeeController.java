package com.example.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.employee.entity.Employee;
import com.example.employee.response.EmployeeResponse;
import com.example.employee.service.EmployeeServiceInt;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServiceInt employeeServiceInt;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody Employee employee) {
        EmployeeResponse response = employeeServiceInt.createEmployee(employee);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/fetch/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR') or hasRole('USER')")
    public ResponseEntity<EmployeeResponse> getAllEmployees() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        
        EmployeeResponse response = employeeServiceInt.getAllEmployees();
        
        // Additional filtering can be added here based on role if needed
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/fetch/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR') or hasRole('USER')")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable("id") String id) {
        EmployeeResponse response = employeeServiceInt.getEmployeeById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<EmployeeResponse> updateEmployee(@RequestBody Employee employee) {
        EmployeeResponse response = employeeServiceInt.updateEmployee(employee);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponse> deleteEmployee(@PathVariable("id") String id) {
        EmployeeResponse response = employeeServiceInt.deleteEmployeeById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}