package io.github.thang86.repository;


import io.github.thang86.entities.Store;
import io.github.thang86.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
*  StoreRepository.java
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
public interface StoreRepository extends JpaRepository<Store, Long> {
	List<Store> findAllByStatus(StoreStatus storeStatus);
	List<Store> findByStoreOwner_IdAndName(Long id, String Name);
	List<Store> findByStoreOwner_Id(Long id);
	List<Store> findByStoreOwner_IdAndStatus(Long id, StoreStatus storeStatus);

	List<Store> findAllByStoreOwner_IdOrCollaborators_User_IdAndStatus(Long id1, Long id2, StoreStatus storeStatus);
    Optional<Store> findByName(String name);
}
