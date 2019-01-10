package io.github.thang86.repository;


import io.github.thang86.entities.PhysicalProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
*  PhysicalProductRepository.java
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
public interface PhysicalProductRepository extends JpaRepository<PhysicalProduct, Long> {
	Optional<PhysicalProduct>findById(Long id);
}
