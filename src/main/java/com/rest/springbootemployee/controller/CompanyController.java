package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.controller.mapper.CompanyMapper;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("companies")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    public CompanyController(CompanyService companyService, CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @GetMapping
    public List<CompanyResponse> getAllCompanies(){
        List<CompanyResponse> companyResponseList = new ArrayList<>();
        List<Company> companyList =  companyService.findAll();
        companyList.forEach(company -> companyResponseList.add(companyMapper.toResponse(company)));
        return companyResponseList;
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Integer id){
        return companyService.findCompanyById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getCompanyAllEmployeesByCompanyId(@PathVariable Integer id){
        return companyService.findCompanyAllEmployeesByCompanyId(id);
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Company> getCompaniesByPageAndPageSize(@RequestParam Integer page,@RequestParam Integer pageSize){
        return companyService.findCompaniesByPageAndPageSize(page,pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company saveCompany(@RequestBody Company company){
        return companyService.addCompany(company);
    }

    @PutMapping("/{id}")
    public Company updateCompanyById(@PathVariable Integer id,@RequestBody List<Employee> employees){
        return companyService.update(id,employees);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyById(@PathVariable Integer id){
        companyService.deleteCompanyById(id);
    }
}
