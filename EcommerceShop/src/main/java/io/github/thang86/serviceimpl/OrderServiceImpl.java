package io.github.thang86.serviceimpl;


import io.github.thang86.repository.OrderRepository;
import io.github.thang86.repository.StoreProductRepository;
import io.github.thang86.entities.Order;
import io.github.thang86.entities.StoreProduct;
import io.github.thang86.entities.User;
import io.github.thang86.enums.OrderStatus;
import io.github.thang86.forms.AddOrderForm;
import io.github.thang86.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
*  OrderServiceImpl.java
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

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    StoreProductRepository storeProductRepository;

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findOneById(id);
    }

    @Override
    public Order addOrder(User user, StoreProduct storeProduct, AddOrderForm addOrderForm) {
        Order order=new Order(user,storeProduct,addOrderForm);
        return orderRepository.save(order);
    }

    @Override
    public Collection<Order> getOrders(Long id, OrderStatus orderStatus) {
        return orderRepository.findAllByUser_IdAndOrderStatusOrderByProcessedDateDesc(id,orderStatus);
    }

    @Override
    public Order changeStatus(Long id) {
        Optional<Order> order = getOrderById(id);
        Order order1=order.get();
        order1.setOrderStatus(OrderStatus.PROCESSED);
        order1.setProcessedDate(new Date());
        return orderRepository.save(order1);
    }

    public List<Order> changeStatus(Collection<Order> orders) {
        for(Order order1 : orders){
            order1.setOrderStatus(OrderStatus.PROCESSED);
            order1.setProcessedDate(new Date());
        }
        return orderRepository.save(orders);
    }

	@Override
	public Integer checkout(Long userId) {
        Collection<Order> orders = getOrders(userId,OrderStatus.UNPROCESSED);

        if(orders.isEmpty())
            return 0;


        return changeStatus(orders).size();
	}

	@Override
	public Order finishOrder(Long orderId) {
        Optional<Order> order = Optional.ofNullable(orderRepository.findOne(orderId));
        if(order.isPresent()) {
            Order order1 = order.get();
            order1.setOrderStatus(OrderStatus.DELIVERED);
            order1.setDeliveredDate(new Date());
            return orderRepository.save(order1);
        }
        return null;
	}

	@Override
	public Integer getOrderCountByUser(Long id, OrderStatus orderStatus) {
		return orderRepository.countOrdersByUser_IdAndOrderStatus(id, orderStatus);
	}

	@Override
    public Collection<Order> getAllProcessedByStore(Long id, OrderStatus orderStatus) {
        Collection<StoreProduct>products=storeProductRepository.findAllByStoreId(id);
        Collection<Order>allOrders=orderRepository.findAllByOrderStatus(orderStatus);
        Collection<Order>orders=new ArrayList<>();
        for(Order order:allOrders) {
            if (products.contains(order.getStoreProduct()))
            {
                orders.add(order);
            }
        }
        return orders;
    }

	@Override
	public Collection<Order> getAllByUser(Long id, OrderStatus orderStatus) {
		return orderRepository.findAllByUser_IdAndOrderStatus(id,orderStatus) ;
	}
}
