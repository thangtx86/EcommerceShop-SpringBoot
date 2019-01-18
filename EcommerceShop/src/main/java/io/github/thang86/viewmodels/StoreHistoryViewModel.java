package io.github.thang86.viewmodels;


import io.github.thang86.entities.Store;
import io.github.thang86.service.StoreHistoryService;
import io.github.thang86.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
*  StoreHistoryViewModel.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2018-12-13    ThangTX     Create
*/

@Component
public class StoreHistoryViewModel {
    @Autowired
    StoreService storeService;
    @Autowired
    StoreHistoryService storeHistoryService;
    public HashMap<String, Object> create(Long Id) {
        HashMap<String, Object> model = new HashMap<>();
        Collection<Store> stores =storeService.getAllAcceptedUserStores(Id);
        for (Store store :  stores)
            Collections.reverse(store.getHistory());
        model.put("stores", stores);
        return model;
    }
}
