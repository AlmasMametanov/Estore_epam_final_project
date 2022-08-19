package com.epam.estore.database.dao.interfaces;

import com.epam.estore.entity.Order;
import java.util.List;

public interface OrderDAO {
    void insertOrder(Order order);
    Long getLastOrderIdByUserId(Long userId);
    List<Order> getAllOrdersByUserId(Long userId);
    void updateOrderStatusByOrderId(Long statusId, Long orderId);
}
