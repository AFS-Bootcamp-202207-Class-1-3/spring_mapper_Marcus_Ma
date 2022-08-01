package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.controller.dto.CompanyRequest;
import com.rest.springbootemployee.controller.dto.CompanyResponse;
import com.rest.springbootemployee.controller.dto.EmployeeRequest;
import com.rest.springbootemployee.controller.dto.EmployeeResponse;
import com.rest.springbootemployee.controller.mapper.CompanyMapper;
import com.rest.springbootemployee.controller.mapper.EmployeeMapper;
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

    @Autowired
    EmployeeMapper employeeMapper;

    public CompanyController(CompanyService companyService, CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @GetMapping
    public List<CompanyResponse> getAllCompanies() {
        List<CompanyResponse> companyResponseList = new ArrayList<>();
        List<Company> companyList = companyService.findAll();
        companyList.forEach(company -> companyResponseList.add(companyMapper.toResponse(company)));
        return companyResponseList;
    }

    @GetMapping("/{id}")
    public CompanyResponse getCompanyById(@PathVariable Integer id) {
        return companyMapper.toResponse(companyService.findCompanyById(id));
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeResponse> getCompanyAllEmployeesByCompanyId(@PathVariable Integer id) {
        List<EmployeeResponse> employeeResponseList = new ArrayList<>();
        List<Employee> employees = companyService.findCompanyAllEmployeesByCompanyId(id);
        employees.forEach(employee -> employeeResponseList.add(employeeMapper.toResponse(employee)));
        return employeeResponseList;
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getCompaniesByPageAndPageSize(@RequestParam Integer page, @RequestParam Integer pageSize) {
        List<CompanyResponse> companyResponseList = new ArrayList<>();
        List<Company> companyList = companyService.findCompaniesByPageAndPageSize(page, pageSize);
        companyList.forEach(company -> companyResponseList.add(companyMapper.toResponse(company)));
        return companyResponseList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse saveCompany(@RequestBody CompanyRequest companyRequest) {
        Company company = companyMapper.toEntity(companyRequest);
        return companyMapper.toResponse(companyService.addCompany(company));
    }

    @PutMapping("/{id}")
    public CompanyResponse updateCompanyById(@PathVariable Integer id, @RequestBody List<EmployeeRequest> employeeRequestList) {
        List<Employee> employeeList = new ArrayList<>();
        employeeRequestList.forEach(employeeRequest -> employeeList.add(employeeMapper.toEntity(employeeRequest)));
        return companyMapper.toResponse(companyService.update(id, employeeList));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyById(@PathVariable Integer id) {
        companyService.deleteCompanyById(id);
    }
}
