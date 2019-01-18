package io.github.thang86.viewmodels;


import io.github.thang86.repository.OrderRepository;
import io.github.thang86.entities.Store;
import io.github.thang86.service.OrderService;
import io.github.thang86.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;

/**
*  StoreOwnerDashboardViewModel.java
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
public class StoreOwnerDashboardViewModel {

	@Autowired
	StoreService storeService;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderRepository orderRepository;

	//Get User Stores.
	public HashMap<String, Object> create(Long Id) {
		HashMap<String, Object> model = new HashMap<>();
		Collection<Store> Accepted = storeService.getAllAcceptedUserStores(Id);
		Collection<Store> Pending = storeService.getAllPendingUserStores(Id);
		Collection<Store> Rejected = storeService.getAllNotAcceptedUserStores(Id);
		Collection<Store> Collaborated = storeService.getAllCollaboratedUserStores(Id);
		model.put("accepted", Accepted);
		model.put("pending", Pending);
		model.put("rejected", Rejected);
		model.put("collaborated", Collaborated);

		return model;
	}

}
