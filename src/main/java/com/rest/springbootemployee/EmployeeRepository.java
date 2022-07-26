package com.rest.springbootemployee;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
}
