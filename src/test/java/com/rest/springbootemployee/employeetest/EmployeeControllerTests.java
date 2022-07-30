package com.rest.springbootemployee.employeetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.springbootemployee.controller.EmployeeRequest;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@AutoConfigureMockMvc
@SpringBootTest
//@ActiveProfiles("test")
class EmployeeControllerTests {
    @Autowired
    MockMvc client;
    @Autowired
    JpaEmployeeRepository jpaEmployeeRepository;

    @Autowired
    JpaCompanyRepository jpaCompanyRepository;

    int companyId;
    @BeforeEach
    void clearDB() {
        jpaEmployeeRepository.deleteAll();
        jpaCompanyRepository.deleteAll();
        Company company = jpaCompanyRepository.save(new Company(1,"test", Collections.emptyList()));
        this.companyId = company.getId();
    }

    @Test
    void should_return_allEmployees_when_getAllEmployees_given_none() throws Exception {
        jpaEmployeeRepository.save(new Employee(1, "Lily", 20, "Female", 11000,companyId));
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(11000));
    }

    @Test
    void should_return_employee_when_create_employee_given_employee() throws Exception {
        // given
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName("zs");
        employeeRequest.setAge(20);
        employeeRequest.setGender("Male");
        employeeRequest.setSalary(10000);
        ObjectMapper objectMapper = new ObjectMapper();
        String requstJson = objectMapper.writeValueAsString(employeeRequest);
        // when & then
        client.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requstJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("zs"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(10000));

        // should
        List<Employee> allEmployees = jpaEmployeeRepository.findAll();
        assertThat(allEmployees, hasSize(1));
        assertThat(allEmployees.get(0).getAge(), equalTo(20));
        assertThat(allEmployees.get(0).getName(), equalTo("zs"));
        assertThat(allEmployees.get(0).getGender(), equalTo("Male"));
        assertThat(allEmployees.get(0).getSalary(), equalTo(10000));
    }

    @Test
    void should_return_rightEmployee_when_getEmployeeById_given_Id() throws Exception {
        Employee employee =  jpaEmployeeRepository
                .save(new Employee(1, "Lily", 20, "Female", 11000,companyId));
        client.perform(MockMvcRequestBuilders.get("/employees/{id}",employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(11000));
        client.perform(MockMvcRequestBuilders.get("/employees/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void should_return_none_when_deleteEmployeeById_given_a_Id() throws Exception {

        // given & when
        Employee employee = jpaEmployeeRepository.save(new Employee(1, "Lily", 20, "Female", 11000,companyId));
        client.perform(MockMvcRequestBuilders.delete("/employees/{id}",employee.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // should
        List<Employee> employees = jpaEmployeeRepository.findAll();
        assertThat(employees, hasSize(0));
    }

    @Test
    void should_return_rightEmployee_when_updateEmployee_given_employee() throws Exception {
        // given & when
        Employee originEmployee = jpaEmployeeRepository
                                .save(new Employee(1, "Lily", 20, "Female", 11000,companyId));
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setSalary(10000);
        ObjectMapper objectMapper = new ObjectMapper();
        String requstJson = objectMapper.writeValueAsString(employeeRequest);
//        then

        client.perform(MockMvcRequestBuilders.put("/employees/{id}",originEmployee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requstJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(10000));
    }

    @Test
    void should_return_Employees_when_getEmployeesByGender_given_gender() throws Exception {
        jpaEmployeeRepository.save(new Employee(1, "Lily", 20, "Female", 11000,companyId));
        jpaEmployeeRepository.save(new Employee(2, "Lily", 20, "Male", 9999,companyId));
        client.perform(MockMvcRequestBuilders.get("/employees?gender=Male"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(9999));
    }

    @Test
    void should_return_Employees_when_getEmployeesByPage_given_page_pageSize() throws Exception {
        Employee employee1 = jpaEmployeeRepository.save(new Employee(1, "Lily", 20, "Female", 11000,companyId));
        jpaEmployeeRepository.save(new Employee(2, "Lily", 20, "Male", 9999,companyId));
        jpaEmployeeRepository.save(new Employee(3, "Lily", 20, "Male", 8888,companyId));
        jpaEmployeeRepository.save(new Employee(4, "Lily", 20, "Male", 7777,companyId));
        jpaEmployeeRepository.save(new Employee(5, "Lily", 20, "Male", 6666,companyId));

        client.perform(MockMvcRequestBuilders.get("/employees?page=1&pageSize=3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(employee1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(11000));

        client.perform(MockMvcRequestBuilders.get("/employees?page=2&pageSize=3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].salary").value(6666));
    }
}
