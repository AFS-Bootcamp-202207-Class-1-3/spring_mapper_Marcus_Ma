package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@AutoConfigureMockMvc
@SpringBootTest
public class CompanyControllerTests {
    @Autowired
    MockMvc client;
    @Autowired
    CompanyRepository companyRepository;

    @BeforeEach
    void clearDB() {
        companyRepository.clearAll();
    }

    @Test
    void should_return_allCompanies_when_getAllCompanies_given_none() throws Exception {
//        given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Lily", 20, "Female", 11000));
        companyRepository.save(new Company(1, "spring", employees));
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("spring"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees", hasSize(1)));
    }

    @Test
    void should_return_company_when_create_company_given_company() throws Exception {
        // given
        String newCompany = "{\n" +
                "        \"id\": 9,\n" +
                "        \"companyName\": \"test\",\n" +
                "        \"employees\": [\n" +
                "            {\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"Lily\",\n" +
                "                \"age\": 20,\n" +
                "                \"gender\": \"Female\",\n" +
                "                \"salary\": 11000\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"Lily\",\n" +
                "                \"age\": 20,\n" +
                "                \"gender\": \"Female\",\n" +
                "                \"salary\": 11000\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 3,\n" +
                "                \"name\": \"Lily\",\n" +
                "                \"age\": 20,\n" +
                "                \"gender\": \"Female\",\n" +
                "                \"salary\": 11000\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 4,\n" +
                "                \"name\": \"Lily\",\n" +
                "                \"age\": 20,\n" +
                "                \"gender\": \"Female\",\n" +
                "                \"salary\": 11000\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 5,\n" +
                "                \"name\": \"Leo\",\n" +
                "                \"age\": 20,\n" +
                "                \"gender\": \"Male\",\n" +
                "                \"salary\": 11000\n" +
                "            }\n" +
                "        ]\n" +
                "    }";

        // when & then
        client.perform(MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCompany))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees", hasSize(5)));

        // should
        List<Company> allCompanies = companyRepository.findAllCompanies();
        assertThat(allCompanies, hasSize(1));
        assertThat(allCompanies.get(0).getId(), equalTo(1));
        assertThat(allCompanies.get(0).getCompanyName(), equalTo("test"));
        assertThat(allCompanies.get(0).getEmployees(), hasSize(5));
    }

    @Test
    void should_return_rightCompany_when_getCompanyById_given_Id() throws Exception {
//        given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Lily", 20, "Female", 11000));
        companyRepository.save(new Company(1, "spring", employees));
//        when then
        client.perform(MockMvcRequestBuilders.get("/companies/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("spring"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees", hasSize(1)));
        client.perform(MockMvcRequestBuilders.get("/companies/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void should_return_none_when_deleteCompanyById_given_Id() throws Exception {
        // given & when
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Lily", 20, "Female", 11000));
        companyRepository.save(new Company(1, "spring", employees));
        client.perform(MockMvcRequestBuilders.delete("/companies/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        // should
        List<Company> companies = companyRepository.findAllCompanies();
        assertThat(companies, hasSize(0));
    }

    @Test
    void should_return_rightCompany_when_updateCompany_given_company_Id() throws Exception {
        // given & when
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Lily", 20, "Female", 11000));
        companyRepository.save(new Company(1, "spring", employees));
        String employee = "[\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"Lily\",\n" +
                "        \"age\": 20,\n" +
                "        \"gender\": \"Female\",\n" +
                "        \"salary\": 11000\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"Lily\",\n" +
                "        \"age\": 20,\n" +
                "        \"gender\": \"Female\",\n" +
                "        \"salary\": 11000\n" +
                "    }\n" +
                "]";
//        then
        client.perform(MockMvcRequestBuilders.put("/companies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("spring"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees", hasSize(3)));
    }

    @Test
    void should_return_Companies_when_getEmployeesByPage_given_page_pageSize() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Lily", 20, "Female", 11000));
        companyRepository.save(new Company(1, "spring", employees));
        companyRepository.save(new Company(2, "testA", employees));
        companyRepository.save(new Company(3, "testB", employees));
        companyRepository.save(new Company(4, "testC", employees));
        companyRepository.save(new Company(5, "testD", employees));
        client.perform(MockMvcRequestBuilders.get("/companies?page=1&pageSize=3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("spring"));

        client.perform(MockMvcRequestBuilders.get("/companies?page=2&pageSize=3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyName").value("testD"));
    }

    @Test
    void should_return_employees_when_getCompanyAllEmployeesByCompanyId_given_Id() throws Exception {
        // given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Lily", 20, "Female", 11000));
        employees.add(new Employee(2, "Lily", 20, "Female", 11000));
        companyRepository.save(new Company(1, "spring", employees));

        // when then
        client.perform(MockMvcRequestBuilders.get("/companies/1/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Lily"));

    }
}
