package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NotFoundException;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final JpaCompanyRepository jpaCompanyRepository;

    public CompanyService(JpaCompanyRepository jpaCompanyRepository) {
        this.jpaCompanyRepository = jpaCompanyRepository;
    }

    public List<Company> findAll() {
        return jpaCompanyRepository.findAll();
    }

    public Company findCompanyById(int id) {
        return jpaCompanyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Company.class.getSimpleName()));
    }

    public List<Employee> findCompanyAllEmployeesByCompanyId(int id) {
        Company company = jpaCompanyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Company.class.getSimpleName()));
        return company.getEmployees();
    }

    public List<Company> findCompaniesByPageAndPageSize(int page, int pageSize) {
        return jpaCompanyRepository.findAll(PageRequest.of(page - 1, pageSize)).toList();
    }

    public void deleteCompanyById(int id) {
        jpaCompanyRepository.deleteById(id);
    }

    public Company addCompany(Company company) {
        return jpaCompanyRepository.save(company);
    }

    public Company update(int id, List<Employee> newEmployee) {
        Company company = findCompanyById(id);
        company.addEmployees(newEmployee);
        return jpaCompanyRepository.save(company);
    }
}
