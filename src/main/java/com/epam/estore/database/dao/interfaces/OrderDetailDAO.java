package com.epam.estore.database.dao.interfaces;

import com.epam.estore.entity.OrderDetail;

import java.util.List;

public interface OrderDetailDAO {
    void insertOrderDetail(OrderDetail orderDetail);
    List<OrderDetail> getAllOrderDetailByOrderId(Long orderId);
}
