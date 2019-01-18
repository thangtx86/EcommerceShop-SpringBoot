package io.github.thang86.service;


import io.github.thang86.auth.CurrentUser;
import io.github.thang86.entities.StoreCollabHistory;
import io.github.thang86.entities.StoreHistory;
import io.github.thang86.entities.StoreProductHistory;

import java.util.Collection;
import java.util.Optional;

/**
*  StoreHistoryService.java
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

public interface StoreHistoryService {
    StoreHistory finalize(Long id);
    StoreHistory finalize(StoreHistory storeHistory);
    Boolean undo(Long HistoryId, CurrentUser currentUser);
    Boolean undoCollab(StoreCollabHistory storeCollabHistory, CurrentUser currentUser);
    Boolean undoProduct(StoreProductHistory storeProductHistory, CurrentUser currentUser);
    Collection<StoreHistory> getByStoreID(Long id);
    void add(StoreHistory storeHistory);
    Optional<StoreHistory> getById(Long Id);
}
