package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Employee;

import java.util.ArrayList;
import java.util.List;

public class CompanyRequest {
    private String name;
    private final List<Employee> employees = new ArrayList<>();

    public CompanyRequest() {
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

}
