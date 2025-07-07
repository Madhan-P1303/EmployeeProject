package com.example.employee.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.employee.entity.Employee;
import com.example.employee.enumulator.EmployeeEnum;
import com.example.employee.exception.EmployeeException;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.response.EmployeeResponse;

@Service
public class EmployeeService implements EmployeeServiceInt {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponse createEmployee(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        try {
            Employee savedEmployee = employeeRepository.save(employee);
            response.setResponseStatus(EmployeeEnum.SUCCESS);
            response.setSuccessMessage("Successfully created an Employee");
            response.setCode(201);
            response.setData(savedEmployee);
        } catch (Exception e) {
            response.setResponseStatus(EmployeeEnum.FAILURE);
            response.setErrorMessage(e.getMessage());
            response.setCode(500);
        }
        return response;
    }

    @Override
    public EmployeeResponse getAllEmployees() {
        EmployeeResponse response = new EmployeeResponse();
        try {
            List<Employee> employees = employeeRepository.findAll();
            if (employees.isEmpty()) {
                response.setResponseStatus(EmployeeEnum.SUCCESS);
                response.setSuccessMessage("No employees found");
                response.setCode(204); // No Content
                response.setData(employees);
            } else {
                response.setResponseStatus(EmployeeEnum.SUCCESS);
                response.setSuccessMessage("Successfully retrieved all employees");
                response.setCode(200);
                response.setData(employees);
            }
        } catch (Exception e) {
            response.setResponseStatus(EmployeeEnum.FAILURE);
            response.setErrorMessage(e.getMessage());
            response.setCode(500);
        }
        return response;
    }

    @Override
    public EmployeeResponse getEmployeeById(String id) {
        EmployeeResponse response = new EmployeeResponse();
        try {
            Optional<Employee> optionalEmployee = employeeRepository.findById(id);
            if (optionalEmployee.isPresent()) {
                response.setResponseStatus(EmployeeEnum.SUCCESS);
                response.setSuccessMessage("Successfully retrieved employee");
                response.setCode(200);
                response.setData(optionalEmployee.get());
            } else {
                response.setResponseStatus(EmployeeEnum.FAILURE);
                response.setErrorMessage("Employee not found with id: " + id);
                response.setCode(404);
            }
        } catch (Exception e) {
            response.setResponseStatus(EmployeeEnum.FAILURE);
            response.setErrorMessage(e.getMessage());
            response.setCode(500);
        }
        return response;
    }

    @Override
    public EmployeeResponse updateEmployee(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        try {
            // Check if employee exists
            if (employee.getId() != null && employeeRepository.existsById(employee.getId())) {
                Employee updatedEmployee = employeeRepository.save(employee);
                response.setResponseStatus(EmployeeEnum.SUCCESS);
                response.setSuccessMessage("Successfully updated employee");
                response.setCode(200);
                response.setData(updatedEmployee);
            } else {
                response.setResponseStatus(EmployeeEnum.FAILURE);
                response.setErrorMessage("Employee not found for update");
                response.setCode(404);
            }
        } catch (Exception e) {
            response.setResponseStatus(EmployeeEnum.FAILURE);
            response.setErrorMessage(e.getMessage());
            response.setCode(500);
        }
        return response;
    }

    @Override
    public EmployeeResponse deleteEmployeeById(String id) {
        EmployeeResponse response = new EmployeeResponse();
        try {
            if (employeeRepository.existsById(id)) {
                employeeRepository.deleteById(id);
                response.setResponseStatus(EmployeeEnum.SUCCESS);
                response.setSuccessMessage("Successfully deleted employee");
                response.setCode(200);
                response.setData("Employee with id " + id + " has been deleted");
            } else {
                response.setResponseStatus(EmployeeEnum.FAILURE);
                response.setErrorMessage("Employee not found with id: " + id);
                response.setCode(404);
            }
        } catch (Exception e) {
            response.setResponseStatus(EmployeeEnum.FAILURE);
            response.setErrorMessage(e.getMessage());
            response.setCode(500);
        }
        return response;
    }
}