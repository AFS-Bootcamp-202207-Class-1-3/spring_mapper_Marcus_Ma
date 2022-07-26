package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.exception.NotFoundCompany;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private List<Company> companyList;
    private final EmployeeRepository employeeRepository;
    public CompanyRepository(EmployeeRepository employeeRepository) {
        companyList = new ArrayList<Company>()
        {
            {
                add(new Company(1,"Spring",employeeRepository.findAllEmployees()));
                add(new Company(2,"Summer",employeeRepository.findAllEmployees()));
                add(new Company(3,"Aut",employeeRepository.findAllEmployees()));
                add(new Company(4,"A",employeeRepository.findAllEmployees()));
                add(new Company(5,"BBB",employeeRepository.findAllEmployees()));
            }
        };
        this.employeeRepository = employeeRepository;
    }

    public List<Company> findAllCompanies() {
        return companyList;
    }

    public Company findCompanyById(int id) {
        return companyList.stream()
                .filter(company -> company.getId() == id)
                .findFirst()
                .orElseThrow(()->new NotFoundCompany());
    }
}
