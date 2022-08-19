package com.epam.estore.database.dao.impl;

import static com.epam.estore.database.connection.ConnectionPool.getInstance;
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
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        setAutoCommit();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER)) {
            java.sql.Date sqlDateStart = new java.sql.Date((order.getDateStart()).getTime());
            java.sql.Date sqlDateFinish = new java.sql.Date((order.getDateFinish()).getTime());
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setLong(2, order.getStatusId());
            preparedStatement.setInt(3, order.getTotalCost());
            preparedStatement.setDate(4, sqlDateStart);
            preparedStatement.setDate(5, sqlDateFinish);
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
    public Long getLastOrderIdByUserId(Long userId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        Long orderId = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_ORDER_ID_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderId = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orderId;
    }

    @Override
    public List<Order> getAllOrdersByUserId(Long userId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToOrderList(orders, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orders;
    }


    @Override
    public void updateOrderStatusByOrderId(Long statusId, Long orderId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        setAutoCommit();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS_BY_ORDER_ID)) {
            preparedStatement.setLong(1, statusId);
            preparedStatement.setLong(2, orderId);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollBack();
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private void setParametersToOrder(Order order, ResultSet resultSet) throws SQLException {
        order.setId(resultSet.getLong("id"));
        order.setUserId(resultSet.getLong("user_id"));
        order.setStatusId(resultSet.getLong("status_id"));
        order.setTotalCost(resultSet.getInt("total_cost"));
        order.setDateStart(resultSet.getTimestamp("date_start"));
        order.setDateFinish(resultSet.getTimestamp("date_finish"));
    }

    private void setParametersToOrderList(List<Order> orders, ResultSet resultSet) throws SQLException {
        Order order = new Order();
        setParametersToOrder(order, resultSet);
        orders.add(order);
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
