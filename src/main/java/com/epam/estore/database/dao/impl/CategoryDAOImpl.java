package com.epam.estore.database.dao.impl;

import com.epam.estore.database.connection.ConnectionPool;
import com.epam.estore.database.dao.interfaces.CategoryDAO;
import com.epam.estore.entity.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAOImpl implements CategoryDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_CATEGORY = "INSERT INTO product_category (parent_id) VALUES (?)";
    private static final String GET_LAST_CATEGORY_ID = "SELECT id FROM product_category ORDER BY id DESC LIMIT 1";
    private static final String REMOVE_CATEGORY = "DELETE FROM product_category WHERE id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertCategory(Category category) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATEGORY)) {
            preparedStatement.setInt(1, category.getParentId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
    @Override
    public Integer getLastCategoryId() {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        Integer categoryId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_CATEGORY_ID)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                categoryId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return categoryId;
    }

    @Override
    public void removeCategory(Integer categoryId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_CATEGORY)) {
            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
