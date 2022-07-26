package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NotFoundCompany;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companyList;
    private final EmployeeRepository employeeRepository;

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
        this.employeeRepository = employeeRepository;
    }

    public List<Company> findAllCompanies() {
        return companyList;
    }

    public Company findCompanyById(int id) {
        return companyList.stream()
                .filter(company -> company.getId() == id)
                .findFirst()
                .orElseThrow(NotFoundCompany::new);
    }

    public List<Employee> findCompanyAllEmployeesByCompanyId(int id) {
        return findCompanyById(id).getEmployees();
    }

    public List<Company> findCompaniesByPageAndPageSize(int page, int pageSize) {
        return companyList.stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
