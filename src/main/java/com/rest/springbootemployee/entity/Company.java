package com.rest.springbootemployee.entity;

import java.util.List;

public class Company {
    private int id;
    private String companyName;
    private List<Employee> employees;

    public Company(int id, String companyName, List<Employee> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employees = employees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }


    public List<Employee> getEmployees() {
        return employees;
    }

    public Boolean addEmployees(List<Employee> employees){
        employees.forEach(employee -> this.getEmployees().add(employee));
        return true;
    }

}
