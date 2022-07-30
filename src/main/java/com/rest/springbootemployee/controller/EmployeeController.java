package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.controller.mapper.EmployeeMapper;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    @Autowired
    private EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        List<EmployeeResponse> employeeResponseList = new ArrayList<>();
        employeeService.findAll()
                .forEach(employee -> employeeResponseList
                        .add(employeeMapper.toResponse(employee)));
        return employeeResponseList;
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Integer id) {
        return employeeMapper.toResponse(employeeService.findEmployeesById(id));
    }

    @GetMapping(params = {"gender"})
    public List<EmployeeResponse> getEmployeesByGender(@RequestParam String gender) {
        List<EmployeeResponse> employeeResponseList = new ArrayList<>();
        employeeService.findEmployeesByGender(gender)
                .forEach(employee -> employeeResponseList
                        .add(employeeMapper.toResponse(employee)));
        return employeeResponseList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse saveEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEntity(employeeRequest);
        EmployeeResponse employeeResponse = employeeMapper.toResponse(employeeService.addEmployee(employee));
        return employeeResponse;
    }

    @PutMapping("/{id}")
    public Employee updateEmployeeById(@PathVariable Integer id, @RequestBody Employee employee) {
        return employeeService.update(id, employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeById(@PathVariable Integer id) {
        employeeService.deleteEmployeeById(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeesByPageAndPageSize(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return employeeService.findEmployeesByPage(page, pageSize);
    }
}
