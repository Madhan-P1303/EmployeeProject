package com.example.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.employee.entity.Employee;
import com.example.employee.enumulator.EmployeeEnum;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.response.EmployeeResponse;

@Service
public class EmployeeService implements EmployeeServiceInt {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeResponse createEmployee(Employee employee) {
    	
    	EmployeeResponse response = new EmployeeResponse();
    	try {
        	employeeRepository.save(employee);
        	response.setResponseStatus(EmployeeEnum.SUCCESS);
        	response.setSuccessMessage("Successfully created a Employee");
        	response.setCode(201);
        	response.setData(employee);
    	}catch(Exception e) {
    		response.setResponseStatus(EmployeeEnum.FAILURE);
    		response.setErrorMessage(e.getMessage());
    		response.setCode(500);
    		
    	}
            return response;
    }


}
