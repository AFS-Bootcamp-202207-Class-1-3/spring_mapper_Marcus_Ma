package com.rest.springbootemployee.mapper;

import com.rest.springbootemployee.dto.CompanyRequest;
import com.rest.springbootemployee.dto.CompanyResponse;
import com.rest.springbootemployee.entity.Company;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public Company toEntity(CompanyRequest companyRequest) {
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest, company);
        return company;
    }

    public CompanyResponse toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company, companyResponse);
        return companyResponse;
    }
}
