package com.example.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.entity.Employee;
import com.example.employee.response.EmployeeResponse;
import com.example.employee.service.EmployeeServiceInt;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    
    @Autowired
    EmployeeServiceInt employeeServiceInt;
    
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody Employee employee) {
    	EmployeeResponse response = employeeServiceInt.createEmployee(employee);
    	return ResponseEntity.status(response.getCode()).body(response);
    }
    
   
}