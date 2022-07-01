package com.epam.estore.database.dao.impl;

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
    private static final String REMOVE_PRODUCT_FROM_BASKET = "DELETE FROM basket WHERE product_id = ? AND user_id = ?";
    private static final String REMOVE_BASKET = "DELETE FROM basket WHERE user_id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertBasket(Basket basket) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BASKET)) {
            preparedStatement.setLong(1, basket.getUserId());
            preparedStatement.setLong(2, basket.getProductId());
            preparedStatement.setInt(3, basket.getCount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Basket> getAllBasketsByUserId(Long userId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Basket> baskets = new ArrayList<>();
        Basket basket;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCTS_FROM_BASKET_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                basket = new Basket();
                basket.setProductId(resultSet.getLong("product_id"));
                basket.setUserId(resultSet.getLong("user_id"));
                basket.setCount(resultSet.getInt("count"));
                baskets.add(basket);
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return baskets;
    }

    @Override
    public void removeProductFromBasket(Long productId, Long userId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_PRODUCT_FROM_BASKET)) {
            preparedStatement.setLong(1, productId);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void removeBasket(Long userId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BASKET)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
