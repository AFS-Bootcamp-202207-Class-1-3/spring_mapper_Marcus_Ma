package com.rest.springbootemployee.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String companyName;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "companyId")
    private List<Employee> employees = new ArrayList<>();

    public Company() {
    }

    public Company(Integer id, String companyName, List<Employee> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employees = employees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void addEmployees(List<Employee> employees) {
        employees.forEach(employee -> this.getEmployees().add(employee));
    }

}
