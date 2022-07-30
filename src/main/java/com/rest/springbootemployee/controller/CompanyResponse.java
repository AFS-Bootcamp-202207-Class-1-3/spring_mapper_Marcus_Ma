package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Employee;

import java.util.ArrayList;
import java.util.List;

public class CompanyResponse {
    private Integer id;
    private String name;
    private List<Employee> employees = new ArrayList<>();

    public CompanyResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
