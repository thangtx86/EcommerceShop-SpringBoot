package io.github.thang86.repository;


import io.github.thang86.entities.Order;
import io.github.thang86.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
*  OrderRepository.java
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
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Get Order by Id
     * @param id
     * @return Order
     */
    Optional<Order> findOneById(Long id);

    /**
     *
     * @param id
     * @param orderStatus
     * @return
     */
    Integer countOrdersByUser_IdAndOrderStatus(Long id, OrderStatus orderStatus);

    /**
     *
     * @param Id
     * @param date
     * @param orderStatuses
     * @return Order list
     */
    List<Order> findAllByStoreProduct_Store_IdAndProcessedDateAfterAndOrderStatusIn(Long Id, Date date, List<OrderStatus> orderStatuses);

    /**
     *
     * @param id
     * @param orderStatus
     * @return
     */
    List<Order> findAllByUser_IdAndOrderStatus(Long id, OrderStatus orderStatus);

    /**
     *
     * @param id
     * @param orderStatus
     * @return
     */
    List<Order> findAllByUser_IdAndOrderStatusOrderByProcessedDateDesc(Long id, OrderStatus orderStatus);

    /**
     *
     * @param orderStatus
     * @return Get
     */
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);
}
