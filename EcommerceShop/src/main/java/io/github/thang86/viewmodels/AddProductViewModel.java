package io.github.thang86.viewmodels;


import io.github.thang86.forms.AddProductForm;
import io.github.thang86.service.BrandService;
import io.github.thang86.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
*  AddProductViewModel.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2018-12-13    ThangTX     Create
*/

@Component
public class AddProductViewModel {
	@Autowired
	BrandService brandService;

	@Autowired
	CompanyService companyService;

	//Query for Brands/Companies using the view model!.
	public HashMap<String, Object> create(AddProductForm form) {
		HashMap<String, Object> model = new HashMap<>();
		model.put("addProductForm"  , form);
		model.put("brands"          , brandService.getAllBrands());
		model.put("companies"       , companyService.getAllCompanies());
		return model;
	}

}
