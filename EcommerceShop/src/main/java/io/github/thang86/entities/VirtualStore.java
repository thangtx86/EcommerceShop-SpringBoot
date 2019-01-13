package io.github.thang86.entities;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;


/**
*  VirtualStore.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2018-12-12    ThangTX     Create
*/



@Entity
@Indexed
@DiscriminatorValue(value = "virtual")
public class VirtualStore extends Store {

	@Override
	public List<StoreProduct> getStoreProducts() {
		return storeProducts;
	}

	@Override
	public boolean addStoreProduct(StoreProduct storeProduct) {
		if(storeProduct.getProduct() instanceof VirtualProduct){
			if(storeProducts == null)
				storeProducts = new ArrayList<>();
			return storeProducts.add(storeProduct);
		}
		return false;
	}

	@Override
	public boolean setStoreProducts(List<StoreProduct> storeProducts) {
		this.storeProducts = storeProducts;
		return true;
	}
}

