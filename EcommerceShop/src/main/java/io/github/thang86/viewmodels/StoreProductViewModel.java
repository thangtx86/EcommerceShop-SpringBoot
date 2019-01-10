package io.github.thang86.viewmodels;


import io.github.thang86.entities.PhysicalProduct;
import io.github.thang86.entities.StoreProduct;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
*  StoreProductViewModel.java
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
public class StoreProductViewModel {

	//Check if Product is Physical/Virtual
	public HashMap<String, Object> create(StoreProduct storeProduct) {
		HashMap<String, Object> model = new HashMap<>();
		model.put("product"  , storeProduct);

		//We should use attributes instead of passing a whole obj, but mehh... msh 3ayz a8yar el view bt3at Refaie.
		PhysicalProduct physicalProduct = null;
		if(storeProduct.getProduct() instanceof PhysicalProduct)
			physicalProduct =  (PhysicalProduct) storeProduct.getProduct();

		model.put("physicalproduct"  , physicalProduct);
		return model;
	}

}
