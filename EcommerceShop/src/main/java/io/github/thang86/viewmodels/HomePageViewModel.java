package io.github.thang86.viewmodels;


import io.github.thang86.entities.StoreProduct;
import io.github.thang86.service.StoreProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;

/**
*  HomePageViewModel.java
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
public class HomePageViewModel {
    @Autowired
    StoreProductService storeProductService;

    //Getting all products
    public HashMap<String, Object> create() {
        HashMap<String, Object> model = new HashMap<>();
        Collection<StoreProduct> products=storeProductService.getTop30();
        model.put("products",products);
        return model;
    }

}
