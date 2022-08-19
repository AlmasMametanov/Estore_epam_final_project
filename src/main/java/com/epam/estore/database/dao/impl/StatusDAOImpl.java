package com.epam.estore.database.dao.impl;

import static com.epam.estore.database.connection.ConnectionPool.getInstance;
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
    public Long getStatusIdByNameAndLocaleId(String statusName, Long localeId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        Long statusId = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_STATUS_ID_BY_NAME_AND_LOCALE_ID)) {
            preparedStatement.setString(1, statusName);
            preparedStatement.setLong(2, localeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                statusId = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return statusId;
    }

    @Override
    public Status getStatusById(Long statusId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        Status status = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_STATUS_BY_ID)) {
            preparedStatement.setLong(1, statusId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                status = new Status();
                setParametersToStatus(status, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return status;
    }

    @Override
    public List<Status> getAllStatusByLocaleId(Long localeId) {
        connectionPool = getInstance();
        connection = connectionPool.getConnection();
        List<Status> statusList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_STATUS_BY_LOCALE_ID)) {
            preparedStatement.setLong(1, localeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToStatusList(statusList, resultSet);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return statusList;
    }

    private void setParametersToStatus(Status status, ResultSet resultSet) throws SQLException {
        status.setId(resultSet.getLong("id"));
        status.setLocaleId(resultSet.getLong("locale_id"));
        status.setName(resultSet.getString("name"));
    }

    private void setParametersToStatusList(List<Status> statusList, ResultSet resultSet) throws SQLException {
        Status status = new Status();
        setParametersToStatus(status, resultSet);
        statusList.add(status);
    }
}
