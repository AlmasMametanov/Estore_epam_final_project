package com.epam.estore.database.dao.impl;

import com.epam.estore.database.connection.ConnectionPool;
import com.epam.estore.database.dao.interfaces.OrderDetailDAO;
import com.epam.estore.entity.OrderDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_ORDER_DETAIL = "INSERT INTO order_detail (order_id, product_id, count, cost) " +
            "VALUES (?, ?, ?, ?)";
    private static final String GET_All_ORDER_DETAIL_BY_ORDER_ID = "SELECT * FROM order_detail WHERE order_id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertOrderDetail(OrderDetail orderDetail) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_DETAIL)) {
            preparedStatement.setLong(1, orderDetail.getOrderId());
            preparedStatement.setLong(2, orderDetail.getProductId());
            preparedStatement.setInt(3, orderDetail.getCount());
            preparedStatement.setInt(4, orderDetail.getCost());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<OrderDetail> getAllOrderDetailByOrderId(Long orderId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_All_ORDER_DETAIL_BY_ORDER_ID)) {
            preparedStatement.setLong(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderDetail = new OrderDetail();
                orderDetail.setId(resultSet.getLong("id"));
                orderDetail.setOrderId(resultSet.getLong("order_id"));
                orderDetail.setProductId(resultSet.getLong("product_id"));
                orderDetail.setCount(resultSet.getInt("count"));
                orderDetail.setCost(resultSet.getInt("cost"));
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orderDetails;
    }
}
