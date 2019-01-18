package io.github.thang86.repository;


import io.github.thang86.entities.StoreHistory;
import io.github.thang86.enums.StoreHistoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
*  StoreHistoryRepository.java
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
public interface StoreHistoryRepository extends JpaRepository<StoreHistory, Long> {
    Optional<StoreHistory> findOneById(Long id);
    List<StoreHistory> findAllByType(StoreHistoryType storeHistoryType);
    List<StoreHistory> findAll();
    List<StoreHistory> findAllByStoreId(Long id);

}


