package com.rest.springbootemployee.companytest;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import com.rest.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CompanyServiceTest {
    @Spy
    CompanyRepository companyRepository = new CompanyRepository(new EmployeeRepository());
    @Spy
    JpaCompanyRepository jpaCompanyRepository;
    @InjectMocks
    CompanyService companyService;

    @Test
    void should_return_allCompanies_when_findAllCompanies_given_none() {
        // given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Lily", 20, "Female", 11000));
        Company company = new Company(1, "spring", employees);
        List<Company> companies = new ArrayList<>();
        companies.add(company);
        given(jpaCompanyRepository.findAll()).willReturn(companies);
        // when
        List<Company> actualCompanies = companyService.findAll();

        // then
        assertThat(actualCompanies, hasSize(1));
        assertThat(actualCompanies.get(0), equalTo(company));
    }

    @Test
    void should_return_company_when_findCompanyById_given_id() {
        // given
        int id = 1;
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Lily", 20, "Female", 11000));
        Company company = new Company(id, "spring", employees);
        given(jpaCompanyRepository.save(company)).willReturn(company);
        jpaCompanyRepository.save(company);
        given(jpaCompanyRepository.findById(id)).willReturn(Optional.of(company));
        // when
        Company actualCompany = companyService.findCompanyById(id);
        // then
        assertThat(actualCompany, equalTo(company));
    }

    @Test
    void should_return_employees_when_findCompanyEmployeesById_given_id() {
        // given
        int id = 1;
        Company company = new Company(id, "spring", Collections.emptyList());
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Lily", 20, "Female", 11000,company.getId()));
        company.setEmployees(employees);
        given(jpaCompanyRepository.findById(id)).willReturn(Optional.of(company));
        // when
        List<Employee> actualEmployees = companyService.findCompanyAllEmployeesByCompanyId(id);
        // then
        assertThat(actualEmployees, equalTo(employees));
    }

    @Test
    void should_return_companies_when_findCompaniesByPage_given_page() {
        // given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Lily", 20, "Female", 11000));
        Company company = new Company(1, "spring", employees);
        List<Company> companies = new ArrayList<>();
        companies.add(company);
        given(jpaCompanyRepository.findAll(PageRequest.of(0,5))).willReturn(new PageImpl<>(companies));
        // when
        List<Company> actualCompanies = companyService.findCompaniesByPageAndPageSize(1, 5);
        // then
        assertThat(actualCompanies, hasSize(1));
        assertThat(actualCompanies.get(0), equalTo(company));
    }

    @Test
    void should_return_noContent_when_deleteCompanyById_given_id() {
        // given
        int id = 1;
        // when
        companyService.deleteCompanyById(id);
        // then
        verify(jpaCompanyRepository).deleteById(id);
    }

    @Test
    void should_return_company_when_createCompany_given_company() {
        // given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Lily", 20, "Female", 11000));
        Company company = new Company(1, "spring", employees);
        given(companyRepository.save(company)).willReturn(company);
        // when
        Company actualCompany = companyService.addCompany(company);
        // then
        assertThat(actualCompany, equalTo(company));
    }

    @Test
    void should_return_updatedCompany_when_update_given_company() {
        // given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Lily", 20, "Female", 11000));
        Company oldCompany = new Company(1, "spring", employees);
        List<Employee> newEmployee = new ArrayList<>();
        employees.add(new Employee(2, "test", 20, "Male", 11000));
        given(companyRepository.findCompanyById(1)).willReturn(oldCompany);
//        service
        given(companyRepository.update(oldCompany, newEmployee)).willCallRealMethod();
        // when
        companyService.update(1, newEmployee);
        // then
        verify(companyRepository).update(oldCompany, newEmployee);
//        assertThat(updatedEmployee.getSalary(), equalTo(newEmployee.getSalary()));
    }
}
