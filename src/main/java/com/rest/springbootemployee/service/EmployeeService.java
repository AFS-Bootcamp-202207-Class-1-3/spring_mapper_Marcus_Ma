package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    public List<Employee> findAll() {
        return employeeRepository.findAllEmployees();
    }

    public Employee update(int id, Employee newEmployee) {
        Employee employee = employeeRepository.findEmployeeById(id);
        System.out.println(employee);
        return employeeRepository.update(employee, newEmployee);
    }
}
