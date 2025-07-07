package com.example.employee.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.employee.entity.Employee;
import com.example.employee.response.EmployeeResponse;

@Service
public interface EmployeeServiceInt {
    
    public EmployeeResponse createEmployee(Employee employee);
    
    public EmployeeResponse getAllEmployees();
    public EmployeeResponse getEmployeeById(String id);
    public EmployeeResponse updateEmployee(Employee employee);
    public EmployeeResponse deleteEmployeeById(String id);
 
}