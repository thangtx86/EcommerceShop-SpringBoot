package io.github.thang86.serviceimpl;


import io.github.thang86.repository.BrandRepository;
import io.github.thang86.entities.Brand;
import io.github.thang86.forms.AddBrandForm;
import io.github.thang86.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
*  BrandServiceImpl.java
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
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Collection<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand addBrand(AddBrandForm form) {
        Brand brand=new Brand();
        brand.setName(form.getName());
        return brandRepository.save(brand);
    }
}
