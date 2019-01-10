package io.github.thang86.service;


import io.github.thang86.entities.Company;
import io.github.thang86.forms.AddCompanyForm;

import java.util.Collection;

/**
*  CompanyService.java
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

public interface CompanyService {
    Collection<Company> getAllCompanies();
    Company addCompany(AddCompanyForm form);
}
