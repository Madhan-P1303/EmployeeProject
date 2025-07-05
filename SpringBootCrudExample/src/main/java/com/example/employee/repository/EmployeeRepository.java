package com.example.employee.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.employee.entity.Employee;

public interface EmployeeRepository extends MongoRepository<Employee,String>{
  
}
