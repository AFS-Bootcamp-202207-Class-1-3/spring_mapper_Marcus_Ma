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
public class CompanyControllerTest {
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
        companyRepository.save(new Company(1, "spring",employees));
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("spring"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees",hasSize(1)));
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees",hasSize(5)));

        // should
        List<Company> allCompanies = companyRepository.findAllCompanies();
        assertThat(allCompanies, hasSize(1));
        assertThat(allCompanies.get(0).getId(), equalTo(1));
        assertThat(allCompanies.get(0).getCompanyName(), equalTo("test"));
        assertThat(allCompanies.get(0).getEmployees(), hasSize(5));
    }

}
