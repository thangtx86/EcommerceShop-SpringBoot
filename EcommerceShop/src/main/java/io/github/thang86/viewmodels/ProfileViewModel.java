package io.github.thang86.viewmodels;


import io.github.thang86.entities.User;
import io.github.thang86.enums.OrderStatus;
import io.github.thang86.service.OrderService;
import io.github.thang86.service.StoreService;
import io.github.thang86.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

/**
*  ProfileViewModel.java
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
public class ProfileViewModel {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    StoreService storeService;

    public HashMap<String,Object> create(User user)
    {
        HashMap<String, Object> model = new HashMap<>();
        model.put("orders",orderService.getAllByUser(user.getId(), OrderStatus.PROCESSED));
        model.put("deliveredOrders",orderService.getAllByUser(user.getId(), OrderStatus.DELIVERED));
        model.put("user",user);
        model.put("stores", user.getStoreOwner() != null ? storeService.getAllAcceptedUserStores(user.getId()) : new ArrayList<>());
        return model;
    }
}
