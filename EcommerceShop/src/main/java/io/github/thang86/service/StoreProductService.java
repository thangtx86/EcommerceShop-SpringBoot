package io.github.thang86.service;


import io.github.thang86.entities.StoreProduct;

import java.util.Collection;
import java.util.Optional;

/**
*  StoreProductService.java
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

public interface StoreProductService {
	Optional<StoreProduct>getProductById(Long id);
	void incrementViews(StoreProduct storeProduct);
	Collection<StoreProduct> getTop30();
	void remove(StoreProduct storeProduct);
}
