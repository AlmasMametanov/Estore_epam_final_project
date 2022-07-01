package com.epam.estore.database.dao.impl;

import com.epam.estore.database.connection.ConnectionPool;
import com.epam.estore.database.dao.interfaces.CountryDAO;
import com.epam.estore.entity.Country;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDAOImpl implements CountryDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String GET_ALL_COUNTRY_BY_LOCALE_ID = "SELECT * FROM country WHERE locale_id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public List<Country> getAllCountryByLocaleId(Integer localeId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Country> countries = new ArrayList<>();
        Country country;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_COUNTRY_BY_LOCALE_ID)) {
            preparedStatement.setInt(1, localeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                country = new Country();
                country.setId(resultSet.getInt("id"));
                country.setLocaleId(resultSet.getInt("locale_id"));
                country.setName(resultSet.getString("name"));
                countries.add(country);
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return countries;
    }
}
