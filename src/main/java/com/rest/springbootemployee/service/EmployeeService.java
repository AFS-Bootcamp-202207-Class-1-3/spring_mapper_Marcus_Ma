package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    JpaEmployeeRepository jpaEmployeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository,JpaEmployeeRepository jpaEmployeeRepository) {
        this.employeeRepository = employeeRepository;
        this.jpaEmployeeRepository = jpaEmployeeRepository;
    }

    public List<Employee> findAll() {
        return jpaEmployeeRepository.findAll();
    }

    public Employee update(Integer id, Employee newEmployee) {
        Employee employee = employeeRepository.findEmployeeById(id);
        return employeeRepository.update(employee, newEmployee);
    }

    public List<Employee> findEmployeesByGender(String gender) {
        return employeeRepository.findEmployeesByGender(gender);
    }

    public List<Employee> findEmployeesByPage(Integer page, Integer pageSize) {
        return employeeRepository.findEmployeesByPageAndPageSize(page, pageSize);
    }

    public Employee findEmployeesById(Integer id) {
        return employeeRepository.findEmployeeById(id);
    }


    public Employee addEmployee(Employee employee) {
        employee.setId(employeeRepository.generateId());
        return employeeRepository.save(employee);
    }

    public void deleteEmployeeById(int id) {
        employeeRepository.delete(id);
    }
}
