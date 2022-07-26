package com.rest.springbootemployee;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employeeList;

    public EmployeeRepository() {
        employeeList = new ArrayList<Employee>() {
            {
                add(new Employee(1, "Lily", 20, "Female", 11000));
                add(new Employee(2, "Lily", 20, "Female", 11000));
                add(new Employee(3, "Lily", 20, "Female", 11000));
                add(new Employee(4, "Lily", 20, "Female", 11000));
                add(new Employee(5, "Leo", 20, "Male", 11000));
            }
        };
    }

    public List<Employee> findAllEmployees() {
        return employeeList;
    }

    public Employee findEmployeeById(int id) {
        return employeeList.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst().orElseThrow(NotFoundEmployee::new);
    }

    public List<Employee> findEmployeesByGender(String gender) {
        return employeeList.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee save(Employee employee) {
        employee.setId(generateId());
        this.employeeList.add(employee);
        return employee;
    }

    private int generateId() {
        int maxId = employeeList.stream()
                .mapToInt(Employee::getId)
                .max()
                .orElse(0);
        return maxId + 1;
    }

    public Employee update(int id, Employee employee) {
        Employee employee1 = employeeList.stream()
                .filter(employee2 -> employee2.getId() == id)
                .findFirst()
                .orElseThrow(NotFoundEmployee::new);
        employee1.update(employee);
        return employee1;
    }

    public Boolean delete(int id) {
        return employeeList.remove(this.findEmployeeById(id));
    }
}
