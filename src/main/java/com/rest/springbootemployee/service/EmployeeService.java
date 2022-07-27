package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NotFoundException;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAllEmployees();
    }

    public Employee update(Integer id, Employee newEmployee) {
        Employee employee = employeeRepository.findEmployeeById(id);
        return employeeRepository.update(employee, newEmployee);
    }

    public List<Employee> findEmployeesByGender(String gender) {
//        List<Employee> employeeList = employeeRepository.findAllEmployees();
//        return employeeList.stream()
//                .filter(employee -> employee.getGender().equals(gender))
//                .collect(Collectors.toList());
        return employeeRepository.findEmployeesByGender(gender);
    }

    public List<Employee> findEmployeesByPage(Integer page, Integer pageSize) {
        return employeeRepository.findEmployeesByPageAndPageSize(page,pageSize);
    }

    public Employee findEmployeesById(Integer id) {
//        List<Employee> employeeList = employeeRepository.findAllEmployees();
//        return employeeList.stream()
//                .filter(employee -> employee.getId().equals(id))
//                .findFirst().orElseThrow(()-> new NotFoundException(Company.class.getSimpleName()));
        return employeeRepository.findEmployeeById(id);
    }


    public Employee addEmployee(Employee employee) {
        employee.setId(employeeRepository.generateId());
        return employeeRepository.save(employee);
    }
}
