package io.github.thang86.repository;


import io.github.thang86.entities.StoreProduct;
import io.github.thang86.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
*  StoreProductRepository.java
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

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct, Long> {
	Optional<StoreProduct> findById(Long id);

	List<StoreProduct> findAll();

	List<StoreProduct> findAllTop30ByOrderByIdDesc();

	List<StoreProduct> findAllTop30ByStore_StatusOrderByIdDesc(StoreStatus storeStatus);

	List<StoreProduct> findAllByStoreId(Long id);
}
