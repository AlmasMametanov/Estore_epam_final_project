package com.epam.estore.database.dao.impl;

import com.epam.estore.database.connection.ConnectionPool;
import com.epam.estore.database.dao.interfaces.ProductDAO;
import com.epam.estore.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_PRODUCT = "INSERT INTO product (name, description, cost, count, country_id, " +
            "product_category_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_PRODUCT_BY_ID = "SELECT * FROM product WHERE id = ?";
    private static final String GET_ALL_PRODUCTS_BY_NAME_AND_LOCALE_ID = "SELECT p.id, p.name, p.description, p.cost, p.count, " +
            "p.country_id, p.product_category_id, c.name, pcl.name FROM product p JOIN country c JOIN product_category_locale pcl " +
            "ON p.country_id = c.id AND p.product_category_id = pcl.product_category_id WHERE p.name = ? AND pcl.locale_id = ?";
    private static final String UPDATE_COUNT_BY_ID = "UPDATE product SET count = ? WHERE id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE product SET name = ?, description = ?, cost = ?, count = ?, " +
            "country_id = ?, product_category_id = ? WHERE id = ?";
    private static final String REMOVE_PRODUCT = "DELETE FROM product WHERE id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertProduct(Product product) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getCost());
            preparedStatement.setInt(4, product.getCount());
            preparedStatement.setInt(5, product.getCountryId());
            preparedStatement.setInt(6, product.getCategoryId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Product getProductById(Long productId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        Product product = new Product();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID)) {
            preparedStatement.setLong(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product.setId(resultSet.getLong("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setCost(resultSet.getInt("cost"));
                product.setCount(resultSet.getInt("count"));
                product.setCountryId(resultSet.getInt("country_id"));
                product.setCategoryId(resultSet.getInt("product_category_id"));
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return product;
    }

    @Override
    public List<Product> getAllProductsByName(String productName, Integer localeId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Product> products = new ArrayList<>();
        Product product;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCTS_BY_NAME_AND_LOCALE_ID)) {
            preparedStatement.setString(1, productName);
            preparedStatement.setInt(2, localeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getLong("p.id"));
                product.setName(resultSet.getString("p.name"));
                product.setDescription(resultSet.getString("p.description"));
                product.setCost(resultSet.getInt("p.cost"));
                product.setCount(resultSet.getInt("p.count"));
                product.setCountryId(resultSet.getInt("p.country_id"));
                product.setCategoryId(resultSet.getInt("p.product_category_id"));
                product.setCountryName(resultSet.getString("c.name"));
                product.setCategoryName(resultSet.getString("pcl.name"));
                products.add(product);
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return products;
    }

    @Override
    public void updateCountById(Integer count, Long productId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COUNT_BY_ID)) {
            preparedStatement.setInt(1, count);
            preparedStatement.setLong(2, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void updateProduct(Product product) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getCost());
            preparedStatement.setInt(4, product.getCount());
            preparedStatement.setInt(5, product.getCountryId());
            preparedStatement.setInt(6, product.getCategoryId());
            preparedStatement.setLong(7, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void removeProduct(Long productId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_PRODUCT)) {
            preparedStatement.setLong(1, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
