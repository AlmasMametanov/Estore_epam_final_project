package com.epam.estore.database.dao.impl;

import static com.epam.estore.database.connection.ConnectionPool.getInstance;
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
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        setAutoCommit();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATEGORY)) {
            preparedStatement.setLong(1, category.getParentId());
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
    public Long getLastCategoryId() {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        Long categoryId = 0L;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_CATEGORY_ID)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                categoryId = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return categoryId;
    }

    @Override
    public void removeCategory(Long categoryId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_CATEGORY)) {
            preparedStatement.setLong(1, categoryId);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
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
