package com.example.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody Employee employee) {
        EmployeeResponse response = employeeServiceInt.createEmployee(employee);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<EmployeeResponse> getAllEmployees() {
        EmployeeResponse response = employeeServiceInt.getAllEmployees();
        return ResponseEntity.status(response.getCode()).body(response);
    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable("id") String id) {
        EmployeeResponse response = employeeServiceInt.getEmployeeById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }
    @PutMapping("/update")
    public ResponseEntity<EmployeeResponse> updateEmployee(@RequestBody Employee employee) {
        EmployeeResponse response = employeeServiceInt.updateEmployee(employee);
        return ResponseEntity.status(response.getCode()).body(response);
    }

   
}