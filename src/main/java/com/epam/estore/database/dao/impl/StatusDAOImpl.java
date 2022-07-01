package com.epam.estore.database.dao.impl;

import com.epam.estore.database.connection.ConnectionPool;
import com.epam.estore.database.dao.interfaces.StatusDAO;
import com.epam.estore.entity.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatusDAOImpl implements StatusDAO {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static final String GET_STATUS_BY_ID = "SELECT * FROM status WHERE id = ?";
    private static final String GET_STATUS_ID_BY_NAME_AND_LOCALE_ID = "SELECT id FROM status WHERE name = ? AND locale_id = ?";
    private static final String GET_ALL_STATUS_BY_LOCALE_ID = "SELECT * FROM status WHERE locale_id = ?";

    ConnectionPool connectionPool;
    Connection connection;

    @Override
    public Integer getStatusIdByNameAndLocaleId(String statusName, Integer localeId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        Integer statusId = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_STATUS_ID_BY_NAME_AND_LOCALE_ID)) {
            preparedStatement.setString(1, statusName);
            preparedStatement.setInt(2, localeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                statusId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return statusId;
    }

    @Override
    public Status getStatusById(Integer statusId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        Status status = new Status();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_STATUS_BY_ID)) {
            preparedStatement.setInt(1, statusId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                status.setId(resultSet.getInt("id"));
                status.setLocaleId(resultSet.getInt("locale_id"));
                status.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return status;
    }

    @Override
    public List<Status> getAllStatusByLocaleId(Integer localeId) {
        connectionPool = connectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Status> statuses = new ArrayList<>();
        Status status;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_STATUS_BY_LOCALE_ID)) {
            preparedStatement.setInt(1, localeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                status = new Status();
                status.setId(resultSet.getInt("id"));
                status.setLocaleId(resultSet.getInt("locale_id"));
                status.setName(resultSet.getString("name"));
                statuses.add(status);
            }
        } catch (SQLException e) {
            logger.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return statuses;
    }
}
