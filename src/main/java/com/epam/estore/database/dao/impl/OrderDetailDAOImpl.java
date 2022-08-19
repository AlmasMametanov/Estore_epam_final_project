package com.epam.estore.database.dao.impl;

import static com.epam.estore.database.connection.ConnectionPool.getInstance;
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
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        setAutoCommit();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_DETAIL)) {
            preparedStatement.setLong(1, orderDetail.getOrderId());
            preparedStatement.setLong(2, orderDetail.getProductId());
            preparedStatement.setInt(3, orderDetail.getCount());
            preparedStatement.setInt(4, orderDetail.getCost());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollBack();
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<OrderDetail> getAllOrderDetailByOrderId(Long orderId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<OrderDetail> orderDetails = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_All_ORDER_DETAIL_BY_ORDER_ID)) {
            preparedStatement.setLong(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToOrderDetailList(orderDetails, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orderDetails;
    }

    private void setParametersToOrderDetail(OrderDetail orderDetail, ResultSet resultSet) throws SQLException {
        orderDetail.setId(resultSet.getLong("id"));
        orderDetail.setOrderId(resultSet.getLong("order_id"));
        orderDetail.setProductId(resultSet.getLong("product_id"));
        orderDetail.setCount(resultSet.getInt("count"));
        orderDetail.setCost(resultSet.getInt("cost"));
    }

    private void setParametersToOrderDetailList(List<OrderDetail> orderDetails, ResultSet resultSet) throws SQLException {
        OrderDetail orderDetail = new OrderDetail();
        setParametersToOrderDetail(orderDetail, resultSet);
        orderDetails.add(orderDetail);
    }

    private void setAutoCommit() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    private void rollBack() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
