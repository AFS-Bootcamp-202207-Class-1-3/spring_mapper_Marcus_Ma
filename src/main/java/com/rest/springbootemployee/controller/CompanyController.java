package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("companies")
public class CompanyController {
    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Company> getAllCompanies(){
        return companyRepository.findAllCompanies();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable int id){
        return companyRepository.findCompanyById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getCompanyAllEmployeesByCompanyId(@PathVariable int id){
        return companyRepository.findCompanyAllEmployeesByCompanyId(id);
    }
}
