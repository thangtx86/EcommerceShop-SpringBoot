package io.github.thang86.serviceimpl;


import io.github.thang86.repository.StoreProductRepository;
import io.github.thang86.entities.StoreProduct;
import io.github.thang86.enums.StoreStatus;
import io.github.thang86.service.StoreProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
*  StoreProductServiceImpl.java
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
public class StoreProductServiceImpl implements StoreProductService {

	@Autowired
	private StoreProductRepository storeProductRepository;

	@Override
	public Optional<StoreProduct> getProductById(Long id) {
		return storeProductRepository.findById(id);
	}

	public void incrementViews(StoreProduct storeProduct) {
		storeProduct.setStoreViews(storeProduct.getStoreViews()+1);
			storeProductRepository.save(storeProduct);
	}

	@Override
	public Collection<StoreProduct> getTop30() {
		return storeProductRepository.findAllTop30ByStore_StatusOrderByIdDesc(StoreStatus.ACCEPTED);
	}

	@Override
	public void remove(StoreProduct storeProduct) {
		storeProductRepository.delete(storeProduct);
	}
}
