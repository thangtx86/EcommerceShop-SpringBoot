package io.github.thang86.viewmodels;


import io.github.thang86.forms.AddStoreCollaboratorForm;
import io.github.thang86.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
*  AddStoreCollaboratorViewModel.java
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
public class AddStoreCollaboratorViewModel {
    @Autowired
    private StoreService storeService;

    public HashMap<String, Object> create(AddStoreCollaboratorForm form, Long StoreOwnerId) {
        HashMap<String, Object> model = new HashMap<>();
        model.put("form"        , form);
        model.put("stores"      , storeService.getAllAcceptedUserStores(StoreOwnerId));
        return model;
    }
}
