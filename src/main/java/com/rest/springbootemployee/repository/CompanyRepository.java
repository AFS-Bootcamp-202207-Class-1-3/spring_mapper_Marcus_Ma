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

    public int generateId(){
        return companyList.stream()
                .mapToInt(Company::getId)
                .max()
                .orElse(0) + 1;
    }

    public Company save(Company company) {
        company.setId(generateId());
        companyList.add(company);
        return company;
    }

    public Company update(int id,List<Employee> employees) {
        Company company = findCompanyById(id);
        company.addEmployees(employees);
        return company;
    }

    public Boolean delete(int id) {
        return companyList.remove(findCompanyById(id));
    }

    public void clearAll() {
        companyList.clear();
    }
}
