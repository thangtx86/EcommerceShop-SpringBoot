package io.github.thang86.viewmodels;


import io.github.thang86.entities.StoreProduct;
import io.github.thang86.forms.AddOrderForm;
import io.github.thang86.service.StoreProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
*  AddOrderViewModel.java
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
public class AddOrderViewModel {


	@Autowired
	private StoreProductService storeProductService;

	public HashMap<String, Object> create(AddOrderForm form, Long storeProductId) {
		HashMap<String, Object> model = new HashMap<>();
		model.put("form"        , form);
		model.put("product"      , storeProductService.getProductById(storeProductId).get());
		return model;
	}

	public HashMap<String, Object> create(AddOrderForm form, StoreProduct storeProduct) {
		HashMap<String, Object> model = new HashMap<>();
		model.put("form"        , form);
		model.put("product"      , storeProduct);
		return model;
	}
}
