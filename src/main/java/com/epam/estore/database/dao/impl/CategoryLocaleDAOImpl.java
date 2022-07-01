package com.epam.estore.database.dao.impl;

import com.epam.estore.database.connection.ConnectionPool;
import com.epam.estore.database.dao.interfaces.CategoryLocaleDAO;
import com.epam.estore.entity.CategoryLocale;
import com.epam.estore.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryLocaleDAOImpl implements CategoryLocaleDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String INSERT_CATEGORY_LOCALE = "INSERT INTO product_category_locale (product_category_id, locale_id, name) " +
            "VALUES (?, ?, ?)";
    private static final String REMOVE_CATEGORY_LOCALE = "DELETE FROM product_category_locale WHERE product_category_id = ?";
    private static final String GET_ROOTS_OF_CATEGORY = "with cte1 as (select product_category_id, locale_id, name from product_category_locale), " +
            "cte2 as (select id, parent_id from product_category) " +
            "select id, parent_id, name, cte1.locale_id from cte1 join cte2 " +
            "where cte1.product_category_id = cte2.id and cte2.parent_id is null and cte1.locale_id = ?";
    private static final String GET_SUBCATEGORIES_OF_ROOT_CATEGORY = "with cte1 as (select product_category_id, locale_id, name from product_category_locale), " +
            "cte2 as (select id, parent_id from product_category) " +
            "select id, parent_id, name, cte1.locale_id from cte1 join cte2 " +
            "where cte1.product_category_id = cte2.id and cte2.parent_id = ? and cte1.locale_id = ?";
    private static final String GET_ALL_PRODUCTS_BY_CATEGORY_ID_AND_LOCALE_ID = "select p.id, p.name, p.description, p.cost, p.count, " +
            "p.country_id, p.product_category_id, c.name, pcl.name from product p join country c join product_category_locale pcl " +
            "on p.country_id = c.id and p.product_category_id = pcl.product_category_id where pcl.product_category_id = ? and pcl.locale_id = ?;";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public void insertCategoryLocale(CategoryLocale categoryLocale) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATEGORY_LOCALE)) {
            preparedStatement.setInt(1, categoryLocale.getCategoryId());
            preparedStatement.setInt(2, categoryLocale.getLocaleId());
            preparedStatement.setString(3, categoryLocale.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<CategoryLocale> getRootsOfCategory(Integer localeId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        CategoryLocale categoryLocale;
        List<CategoryLocale> categoryRoots = new ArrayList<>();
        List<CategoryLocale> subcategoriesOfRoots;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ROOTS_OF_CATEGORY)) {
            preparedStatement.setInt(1, localeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                categoryLocale = new CategoryLocale();
                categoryLocale.setCategoryId(resultSet.getInt("id"));
                categoryLocale.setLocaleId(resultSet.getInt("locale_id"));
                categoryLocale.setName(resultSet.getString("name"));
                subcategoriesOfRoots = getSubcategoriesOfRootCategory(categoryLocale.getCategoryId(), categoryLocale.getLocaleId());
                categoryLocale.setSubcategories(subcategoriesOfRoots);
                categoryRoots.add(categoryLocale);
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return categoryRoots;
    }

    @Override
    public List<CategoryLocale> getSubcategoriesOfRootCategory(Integer parentId, Integer localeId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<CategoryLocale> categories = new ArrayList<>();
        List<CategoryLocale> subcategories;
        CategoryLocale categoryLocale;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_SUBCATEGORIES_OF_ROOT_CATEGORY)) {
            preparedStatement.setInt(1, parentId);
            preparedStatement.setInt(2, localeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                categoryLocale = new CategoryLocale();
                categoryLocale.setCategoryId(resultSet.getInt("id"));
                categoryLocale.setLocaleId(resultSet.getInt("locale_id"));
                categoryLocale.setName(resultSet.getString("name"));
                subcategories = getSubcategoriesOfRootCategory(categoryLocale.getCategoryId(), categoryLocale.getLocaleId());
                categoryLocale.setSubcategories(subcategories);
                categories.add(categoryLocale);
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return categories;
    }

    @Override
    public List<Product> getAllProductsByCategoryIdAndLocaleId(Integer subcategoryId, Integer localeId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Product> productsByCategoryId = new ArrayList<>();
        Product product;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCTS_BY_CATEGORY_ID_AND_LOCALE_ID)) {
            preparedStatement.setInt(1, subcategoryId);
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
                productsByCategoryId.add(product);
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return productsByCategoryId;
    }

    @Override
    public void removeCategoryLocale(Integer categoryLocaleId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_CATEGORY_LOCALE)) {
            preparedStatement.setInt(1, categoryLocaleId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
