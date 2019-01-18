package io.github.thang86.service;


import io.github.thang86.entities.Order;
import io.github.thang86.entities.StoreProduct;
import io.github.thang86.entities.User;
import io.github.thang86.enums.OrderStatus;
import io.github.thang86.forms.AddOrderForm;

import java.util.Collection;
import java.util.Optional;

/**
*  OrderService.java
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

public interface OrderService {
   Optional<Order>getOrderById(Long id);
   Order addOrder(User user, StoreProduct storeProduct, AddOrderForm addOrderForm);
   Collection<Order> getAllProcessedByStore(Long id, OrderStatus orderStatus);
   Collection<Order> getAllByUser(Long id, OrderStatus orderStatus);
   Collection<Order> getOrders(Long id, OrderStatus orderStatus);
   Order changeStatus(Long id);
   Integer checkout(Long userId);
   Order finishOrder(Long orderId);
   Integer getOrderCountByUser(Long id, OrderStatus orderStatus);
}
