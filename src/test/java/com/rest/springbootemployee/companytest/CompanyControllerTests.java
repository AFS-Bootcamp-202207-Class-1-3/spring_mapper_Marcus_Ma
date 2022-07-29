package com.rest.springbootemployee.companytest;

import com.rest.springbootemployee.controller.EmployeeRequest;
import com.rest.springbootemployee.controller.EmployeeResponse;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class CompanyControllerTests {
    @Autowired
    MockMvc client;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    JpaCompanyRepository jpaCompanyRepository;
    @Autowired
    JpaEmployeeRepository jpaEmployeeRepository;
    @BeforeEach
    void clearDB() {
        companyRepository.clearAll();
        jpaCompanyRepository.deleteAll();
        jpaEmployeeRepository.deleteAll();
    }

    @Test
    void should_return_allCompanies_when_getAllCompanies_given_none() throws Exception {
//        given

        Company company =  jpaCompanyRepository.save(new Company(1, "spring", Collections.emptyList()));
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(company.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("spring"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees", hasSize(0)));
    }

    @Test
    void should_return_company_when_create_company_given_company() throws Exception {
        // given
        String newCompany = "{\n" +
                "    \"id\": 1,\n" +
                "    \"companyName\": \"test\",\n" +
                "    \"employees\": []\n" +
                "}";
        // when & then
        client.perform(MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCompany))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees", hasSize(0)));

        // should
        List<Company> allCompanies = jpaCompanyRepository.findAll();
        assertThat(allCompanies, hasSize(1));
        assertThat(allCompanies.get(0).getCompanyName(), equalTo("test"));
    }

    @Test
    void should_return_rightCompany_when_getCompanyById_given_Id() throws Exception {
//        given
        Company company =  jpaCompanyRepository.save(new Company(1, "spring", Collections.emptyList()));
//        when then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}",company.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(company.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("spring"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees", hasSize(0)));
        client.perform(MockMvcRequestBuilders.get("/companies/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void should_return_none_when_deleteCompanyById_given_Id() throws Exception {
        // given & when
        Company company = jpaCompanyRepository.save(new Company(1, "spring", Collections.emptyList()));
        client.perform(MockMvcRequestBuilders.delete("/companies/{id}",company.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        // should
        List<Company> companies = jpaCompanyRepository.findAll();
        assertThat(companies, hasSize(0));
    }

    @Test
    void should_return_rightCompany_when_updateCompany_given_company_Id() throws Exception {
        // given & when
        Company company = jpaCompanyRepository.save(new Company(1, "spring", Collections.emptyList()));
//        jpaEmployeeRepository.save(new Employee(1, "Lily", 20, "Female", 11000,company.getId()));
        String employee = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Lily\",\n" +
                "        \"age\": 20,\n" +
                "        \"gender\": \"Female\",\n" +
                "        \"salary\": 11000,\n" +
                "        \"companyId\":"+company.getId()+"\n" +
                "    }\n" +
                "]";
//        then
        client.perform(MockMvcRequestBuilders.put("/companies/{id}",company.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(company.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("spring"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees", hasSize(1)));
    }

    @Test
    void should_return_Companies_when_getCompaniesByPage_given_page_pageSize() throws Exception {
        jpaCompanyRepository.save(new Company(1, "spring", Collections.emptyList()));
        jpaCompanyRepository.save(new Company(2, "testA", Collections.emptyList()));
        jpaCompanyRepository.save(new Company(3, "testB", Collections.emptyList()));
        jpaCompanyRepository.save(new Company(4, "testC", Collections.emptyList()));
        jpaCompanyRepository.save(new Company(5, "testD", Collections.emptyList()));
        client.perform(MockMvcRequestBuilders.get("/companies?page=1&pageSize=3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("spring"));

        client.perform(MockMvcRequestBuilders.get("/companies?page=2&pageSize=3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyName").value("testD"));
    }

    @Test
    void should_return_employees_when_getCompanyAllEmployeesByCompanyId_given_Id() throws Exception {
        // given
        Company company = jpaCompanyRepository.save(new Company(1, "spring", Collections.emptyList()));
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Lily", 20, "Female", 11000,company.getId()));
        employees.add(new Employee(2, "Lily", 20, "Female", 11000,company.getId()));
        company.addEmployees(employees);
        jpaEmployeeRepository.save(new Employee(1, "Lily", 20, "Female", 11000,company.getId()));
        jpaEmployeeRepository.save(new Employee(2, "Lily", 20, "Female", 11000,company.getId()));
        jpaCompanyRepository.save(company);
        // when then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}/employees",company.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Lily"));
    }
}
