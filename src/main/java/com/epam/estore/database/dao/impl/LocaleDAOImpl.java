package com.epam.estore.database.dao.impl;

import com.epam.estore.database.connection.ConnectionPool;
import com.epam.estore.database.dao.interfaces.LocaleDAO;
import com.epam.estore.entity.Locale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocaleDAOImpl implements LocaleDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String GET_LOCALE_ID_BY_SHORT_NAME = "SELECT id FROM locale WHERE short_name = ?";
    private static final String GET_LOCALE_BY_SHORT_NAME = "SELECT * FROM locale WHERE short_name = ?";

    private static final String GET_ALL_LOCALE = "SELECT * FROM locale";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public Integer getLocaleIdByName(String localeShortName) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        Integer locale = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LOCALE_ID_BY_SHORT_NAME)) {
            preparedStatement.setString(1, localeShortName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                locale = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return locale;
    }

    @Override
    public String getLocaleByShortName(String localeShortName) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        String locale = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LOCALE_BY_SHORT_NAME)) {
            preparedStatement.setString(1, localeShortName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                locale = resultSet.getString("short_name");
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return locale;
    }

    @Override
    public List<Locale> getAllLocale() {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Locale> locales = new ArrayList<>();
        Locale locale;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_LOCALE)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                locale = new Locale();
                locale.setId(resultSet.getInt("id"));
                locale.setShortName(resultSet.getString("short_name"));
                locale.setName(resultSet.getString("name"));
                locales.add(locale);
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return locales;
    }
}
