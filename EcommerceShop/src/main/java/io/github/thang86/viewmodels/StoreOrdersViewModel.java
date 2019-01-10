package io.github.thang86.viewmodels;


import io.github.thang86.entities.Order;
import io.github.thang86.entities.Store;
import io.github.thang86.enums.OrderStatus;
import io.github.thang86.service.OrderService;
import io.github.thang86.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
*  StoreOrdersViewModel.java
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
public class StoreOrdersViewModel {

	@Autowired
	StoreService storeService;

	@Autowired
	OrderService orderService;


	//Get Orders per store.
	public HashMap<String, Object> create(Long Id) {
		HashMap<String, Object> model = new HashMap<>();
		Collection<Store> Accepted = storeService.getAllAcceptedUserStores(Id);
		Collection<Collection<Order>> ordersProcessed = new ArrayList<>();
		Collection<Store> storesProcessed = new ArrayList<>();
		Collection<Collection<Order>> ordersDelivered = new ArrayList<>();
		Collection<Store> storesDelivered = new ArrayList<>();

		for (Store store : Accepted) {
			Collection<Order> ordersProcessedPerStore = orderService.getAllProcessedByStore(store.getId(), OrderStatus.PROCESSED);
			Collection<Order> ordersDeliveredPerStore = orderService.getAllProcessedByStore(store.getId(), OrderStatus.DELIVERED);

			if (ordersProcessedPerStore.size() > 0) {
				ordersProcessed.add(ordersProcessedPerStore);
				storesProcessed.add(store);
			}

			if (ordersDeliveredPerStore.size() > 0) {
				ordersDelivered.add(ordersDeliveredPerStore);
				storesDelivered.add(store);
			}
		}

		model.put("storesProcessed", storesProcessed);
		model.put("ordersProcessed", ordersProcessed);

		model.put("storesDelivered", storesDelivered);
		model.put("ordersDelivered", ordersDelivered);


		return model;
	}

}
