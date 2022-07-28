package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NotFoundException;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    JpaEmployeeRepository jpaEmployeeRepository;
    public EmployeeService(JpaEmployeeRepository jpaEmployeeRepository) {
        this.jpaEmployeeRepository = jpaEmployeeRepository;
    }

    public List<Employee> findAll() {
        return jpaEmployeeRepository.findAll();
    }
//    TODO
    public Employee update(Integer id, Employee newEmployee) {
        Employee employee = jpaEmployeeRepository.findById(id).get();
        employee.update(newEmployee);
        return jpaEmployeeRepository.save(employee);
    }
    public List<Employee> findEmployeesByGender(String gender) {
        return jpaEmployeeRepository.findByGender(gender);
    }
    public List<Employee> findEmployeesByPage(Integer page, Integer pageSize) {
        return jpaEmployeeRepository.findAll(PageRequest.of((page - 1),pageSize)).toList();
    }

    public Employee findEmployeesById(Integer id) {
        return jpaEmployeeRepository.findById(id)
                .orElseThrow(()->new NotFoundException(Employee.class.getSimpleName()));
    }

    public Employee addEmployee(Employee employee) {
        return jpaEmployeeRepository.save(employee);
    }
    public void deleteEmployeeById(int id) {
        jpaEmployeeRepository.deleteById(id);
    }

}
