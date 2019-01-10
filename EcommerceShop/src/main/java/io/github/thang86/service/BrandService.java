package io.github.thang86.service;


import io.github.thang86.entities.Brand;
import io.github.thang86.forms.AddBrandForm;

import java.util.Collection;

/**
*  BrandService.java
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

public interface BrandService {
    Collection<Brand> getAllBrands();
    Brand addBrand(AddBrandForm form);
}
