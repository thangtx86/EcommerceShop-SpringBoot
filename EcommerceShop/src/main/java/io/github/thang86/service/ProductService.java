package io.github.thang86.service;


import io.github.thang86.entities.Product;
import io.github.thang86.forms.AddProductForm;

import java.util.Collection;
import java.util.Optional;

/**
*  ProductService.java
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

public interface ProductService {

	Optional<Product>getProductById(Long id);
	Optional<Product>getProductByName(String name);
	Optional<Product>getPriceBetween(Float start, Float end);
	Collection<Product>getAllProducts();
	Product addProduct(AddProductForm productForm);
	void incrementViews(Product product);
}
