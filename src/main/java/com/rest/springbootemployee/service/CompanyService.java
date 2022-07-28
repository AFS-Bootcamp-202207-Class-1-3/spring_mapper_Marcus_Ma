package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NotFoundException;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    private final JpaCompanyRepository jpaCompanyRepository;

    public CompanyService(CompanyRepository companyRepository,JpaCompanyRepository jpaCompanyRepository) {
        this.companyRepository = companyRepository;
        this.jpaCompanyRepository = jpaCompanyRepository;
    }

    public List<Company> findAll() {
        return jpaCompanyRepository.findAll();
    }
    public Company findCompanyById(int id) {
        return jpaCompanyRepository.findById(id)
                .orElseThrow(()->new NotFoundException(Company.class.getSimpleName()));
    }

    public List<Employee> findCompanyAllEmployeesByCompanyId(int id) {
        Company company = jpaCompanyRepository.findById(id)
                .orElseThrow(()->new NotFoundException(Company.class.getSimpleName()));
        return company.getEmployees();
    }

    public List<Company> findCompaniesByPageAndPageSize(int page, int pageSize) {
        return jpaCompanyRepository.findAll(PageRequest.of(page-1,pageSize)).toList();
    }

    public void deleteCompanyById(int id) {
        companyRepository.delete(id);
    }

    public Company addCompany(Company company) {
        company.setId(companyRepository.generateId());
        return companyRepository.save(company);
    }

    public Company update(int id, List<Employee> newEmployee) {
        Company company = companyRepository.findCompanyById(id);
        return companyRepository.update(company, newEmployee);
    }
}
