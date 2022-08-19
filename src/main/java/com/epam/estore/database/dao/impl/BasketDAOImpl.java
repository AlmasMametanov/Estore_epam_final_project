package com.epam.estore.database.dao.impl;

import static com.epam.estore.database.connection.ConnectionPool.getInstance;
import com.epam.estore.database.connection.ConnectionPool;
import com.epam.estore.database.dao.interfaces.BasketDAO;
import com.epam.estore.entity.Basket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasketDAOImpl implements BasketDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_BASKET = "INSERT INTO basket (user_id, product_id, count) VALUES (?, ?, ?)";
    private static final String GET_ALL_PRODUCTS_FROM_BASKET_BY_USER_ID = "SELECT * FROM basket WHERE user_id = ?";
    private static final String CHANGE_PRODUCT_COUNT_IN_BASKET_BY_BASKET_ID = "UPDATE basket SET count = ? WHERE id = ?";
    private static final String REMOVE_PRODUCT_FROM_BASKET = "DELETE FROM basket WHERE product_id = ? AND user_id = ?";
    private static final String REMOVE_BASKET = "DELETE FROM basket WHERE user_id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertBasket(Basket basket) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        setAutoCommit();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BASKET)) {
            preparedStatement.setLong(1, basket.getUserId());
            preparedStatement.setLong(2, basket.getProductId());
            preparedStatement.setInt(3, basket.getCount());
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
    public List<Basket> getAllBasketsByUserId(Long userId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<Basket> baskets = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCTS_FROM_BASKET_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToBasketList(baskets, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return baskets;
    }

    @Override
    public void updateProductCountInBasket(Integer productCount, Long basketId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        setAutoCommit();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PRODUCT_COUNT_IN_BASKET_BY_BASKET_ID)) {
            preparedStatement.setInt(1, productCount);
            preparedStatement.setLong(2, basketId);
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
    public void removeBasketByUserId(Long productId, Long userId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try {
            System.out.println(connection.getAutoCommit());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_PRODUCT_FROM_BASKET)) {
            preparedStatement.setLong(1, productId);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void removeAllBasketsByUserId(Long userId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BASKET)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private void setParametersToBasket(Basket basket, ResultSet resultSet) throws SQLException {
        basket.setId(resultSet.getLong("id"));
        basket.setProductId(resultSet.getLong("product_id"));
        basket.setUserId(resultSet.getLong("user_id"));
        basket.setCount(resultSet.getInt("count"));
    }

    private void setParametersToBasketList(List<Basket> baskets, ResultSet resultSet) throws SQLException {
        Basket basket = new Basket();
        setParametersToBasket(basket, resultSet);
        baskets.add(basket);
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
