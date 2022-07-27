package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;


    public List<Company> findAll() {
        return companyRepository.findAllCompanies();
    }

    public Company findCompanyById(int id) {
        return companyRepository.findCompanyById(id);
    }

    public List<Employee> findCompanyAllEmployeesByCompanyId(int id) {
        return companyRepository.findCompanyAllEmployeesByCompanyId(id);
    }

    public List<Company> findCompaniesByPageAndPageSize(int page, int pageSize) {
        return companyRepository.findCompaniesByPageAndPageSize(page,pageSize);
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
        return companyRepository.update(company,newEmployee);
    }
}
