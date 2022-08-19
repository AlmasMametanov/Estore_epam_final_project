package com.epam.estore.database.dao.impl;

import static com.epam.estore.database.connection.ConnectionPool.getInstance;
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
    public List<Country> getAllCountryByLocaleId(Long localeId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<Country> countries = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_COUNTRY_BY_LOCALE_ID)) {
            preparedStatement.setLong(1, localeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToCountryList(countries, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return countries;
    }

    private void setParameterToCountry(Country country, ResultSet resultSet) throws SQLException {
        country.setId(resultSet.getLong("id"));
        country.setLocaleId(resultSet.getLong("locale_id"));
        country.setName(resultSet.getString("name"));
    }

    private void setParametersToCountryList(List<Country> countries, ResultSet resultSet) throws SQLException {
        Country country = new Country();
        setParameterToCountry(country, resultSet);
        countries.add(country);
    }
}
