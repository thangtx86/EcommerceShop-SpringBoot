package io.github.thang86.serviceimpl;

import io.github.thang86.repository.CompanyRepository;
import io.github.thang86.entities.Company;
import io.github.thang86.forms.AddCompanyForm;
import io.github.thang86.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
*  CompanyServiceImpl.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2018-12-20    ThangTX     Create
*/

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Collection<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company addCompany(AddCompanyForm form) {
        Company company= new Company();
        company.setName(form.getName());
        return companyRepository.save(company);
    }
}
