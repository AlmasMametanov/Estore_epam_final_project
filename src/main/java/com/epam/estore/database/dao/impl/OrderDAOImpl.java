package com.epam.estore.database.dao.impl;

import com.epam.estore.database.connection.ConnectionPool;
import com.epam.estore.database.dao.interfaces.OrderDAO;
import com.epam.estore.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_ORDER = "INSERT INTO orders (user_id, status_id, total_cost, date_start, date_finish) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String GET_LAST_ORDER_ID_BY_USER_ID = "SELECT id FROM orders WHERE user_id = ? ORDER BY id DESC LIMIT 1";
    private static final String GET_ALL_ORDERS_BY_USER_ID = "SELECT * FROM orders WHERE user_id = ?";
    private static final String UPDATE_ORDER_STATUS_BY_ORDER_ID = "UPDATE orders SET status_id = ? WHERE id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertOrder(Order order) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER)) {
            java.sql.Date sqlDateStart = new java.sql.Date((order.getDateStart()).getTime());
            java.sql.Date sqlDateFinish = new java.sql.Date((order.getDateFinish()).getTime());
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setInt(2, order.getStatusId());
            preparedStatement.setInt(3, order.getTotalCost());
            preparedStatement.setDate(4, sqlDateStart);
            preparedStatement.setDate(5, sqlDateFinish);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Long getLastOrderIdByUserId(Long userId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        Long orderId = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_ORDER_ID_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderId = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orderId;
    }

    @Override
    public List<Order> getAllOrdersByUserId(Long userId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Order> orders = new ArrayList<>();
        Order order;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                order = new Order();
                order.setId(resultSet.getLong("id"));
                order.setUserId(resultSet.getLong("user_id"));
                order.setStatusId(resultSet.getInt("status_id"));
                order.setTotalCost(resultSet.getInt("total_cost"));
                order.setDateStart(resultSet.getTimestamp("date_start"));
                order.setDateStart(resultSet.getTimestamp("date_finish"));
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orders;
    }


    @Override
    public void updateOrderStatusByOrderId(Integer statusId, Long orderId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS_BY_ORDER_ID)) {
            preparedStatement.setInt(1, statusId);
            preparedStatement.setLong(2, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
