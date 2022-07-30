package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private final List<Company> companyList;

    public CompanyRepository(EmployeeRepository employeeRepository) {
        companyList = new ArrayList<Company>() {
            {
                add(new Company(1, "Spring", employeeRepository.findAllEmployees()));
                add(new Company(2, "Summer", employeeRepository.findAllEmployees()));
                add(new Company(3, "Aut", employeeRepository.findAllEmployees()));
                add(new Company(4, "A", employeeRepository.findAllEmployees()));
                add(new Company(5, "BBB", employeeRepository.findAllEmployees()));
            }
        };
    }


    public void clearAll() {
        companyList.clear();
    }
}
