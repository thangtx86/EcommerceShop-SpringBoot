package io.github.thang86.controllers.api;


import io.github.thang86.entities.Store;
import io.github.thang86.entities.StoreProduct;
import io.github.thang86.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
*  AutoCompleteController.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2019-01-03    ThangTX     Create
*/

@RestController
public class AutoCompleteController {
	@Autowired
	private SearchService searchService;
	@RequestMapping(value = "api/autocomplete/storeproduct/{queryString}")
	public List<StoreProduct> storeProductList(@PathVariable("queryString") String queryString) {
		if(queryString == null || queryString.isEmpty())
			 return null;
		return searchService.storeProductSearch(queryString, 200);
	}

	@RequestMapping(value = "api/autocomplete/store/{queryString}")
	public List<Store> storeList(@PathVariable(value = "queryString") String queryString) {
		if(queryString == null || queryString.isEmpty())
			return null;
		return searchService.storeSearch(queryString, 100);
	}
}
