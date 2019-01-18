package io.github.thang86.viewmodels;


import io.github.thang86.forms.AddStoreProductForm;
import io.github.thang86.service.ProductService;
import io.github.thang86.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
*  AddStoreProductViewModel.java
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
public class AddStoreProductViewModel {
	@Autowired
	private StoreService storeService;

	@Autowired
	private ProductService productService;

	public HashMap<String, Object> create(AddStoreProductForm form, Long StoreOwnerId) {
		HashMap<String, Object> model = new HashMap<>();
		model.put("form"        , form);
		model.put("stores"      , storeService.getAllUserAndCollabStores(StoreOwnerId));
		model.put("products"    , productService.getAllProducts());
		return model;
	}
}
