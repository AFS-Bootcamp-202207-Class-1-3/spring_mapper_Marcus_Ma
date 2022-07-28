package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import com.rest.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTests {
    @Spy
    JpaEmployeeRepository jpaEmployeeRepository;
    @InjectMocks
    EmployeeService employeeService;


    @Test
    void should_return_allEmployees_when_findAllEmployees_given_none() {
        // given
        Employee employee = new Employee(1, "Lily", 20, "Female", 11000);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        given(jpaEmployeeRepository.findAll()).willReturn(employees);
        // when
        List<Employee> actualEmployees = employeeService.findAll();

        // then
        assertThat(actualEmployees, hasSize(1));
        assertThat(actualEmployees.get(0), equalTo(employee));
    }

    @Test
    void should_return_updatedEmployee_when_update_given_employee() {
        // given
        Employee oldEmployee = new Employee(1, "Lily", 20, "Female", 10000);
        Employee newEmployee = new Employee(1, "Lily", 20, "Female", 12000);
        given(jpaEmployeeRepository.save(oldEmployee)).willReturn(oldEmployee);
        given(jpaEmployeeRepository.findById(oldEmployee.getId())).willReturn(Optional.of(oldEmployee));
        // when
        Employee updatedEmployee = employeeService.update(oldEmployee.getId(), newEmployee);
        // then
        assertThat(updatedEmployee.getSalary(), equalTo(newEmployee.getSalary()));
    }

    @Test
    void should_return_employees_when_findEmployeesByGender_given_gender() {
        // given
        String gender = "Male";
        Employee employee2 = new Employee(2, "Lily", 20, "Male", 10000);
        List<Employee> maleEmployees = new ArrayList<>();
        maleEmployees.add(employee2);
//        given(employeeRepository.findEmployeesByGender(gender)).willReturn(maleEmployees);
        given(jpaEmployeeRepository.findByGender(gender)).willReturn(maleEmployees);
        // when
        List<Employee> actualEmployees = employeeService.findEmployeesByGender(gender);

        // then
        assertThat(actualEmployees, hasSize(1));
        assertThat(actualEmployees.get(0), equalTo(employee2));
    }

    @Test
    void should_return_employees_when_findEmployeesByPage_given_page_pageSize() {
        // given
        Employee employee1 = new Employee(1, "Lily", 20, "Female", 11000);
        Employee employee2 = new Employee(2, "Lily", 20, "Male", 10000);
        Employee employee3 = new Employee(2, "Lily", 20, "Male", 10000);
        Employee employee4 = new Employee(2, "Lily", 20, "Male", 10000);
        Employee employee5 = new Employee(2, "Lily", 20, "Male", 10000);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        employees.add(employee5);
        given(jpaEmployeeRepository.findAll(PageRequest.of(0,5))).willReturn(new PageImpl<>(employees));
        // when
        List<Employee> actualEmployees = employeeService.findEmployeesByPage(1, 5);
        // then
        assertThat(actualEmployees, hasSize(5));
        assertThat(actualEmployees.get(4), equalTo(employee5));
    }

    @Test
    void should_return_employee_when_findEmployeesById_given_Id() {
        // given
        int id = 2;
        Employee employee2 = new Employee(2, "Lily", 20, "Male", 10000);
        given(jpaEmployeeRepository.findById(id)).willReturn(Optional.of(employee2));
        // when
        Employee actualEmployee = employeeService.findEmployeesById(id);
        // then
        assertThat(actualEmployee, equalTo(employee2));
    }

    @Test
    void should_return_employee_when_create_employee_given_new_employee() {
        // given
        Employee employee = new Employee(1, "Lily", 20, "Male", 10000);
//        given(employeeRepository.save(employee)).willReturn(employee);
        given(jpaEmployeeRepository.save(employee)).willReturn(employee);
        // when
        Employee actualEmployee = employeeService.addEmployee(employee);
        // then
        assertThat(actualEmployee, equalTo(employee));
    }

    @Test
    void should_return_noContent_when_delete_employee_given_id() {
        // given
        int id = 1;
        // when
        employeeService.deleteEmployeeById(id);
        // then
        verify(jpaEmployeeRepository).deleteById(id);
    }


}

