package me.lordierclaw.todoserver.database.dao;

import me.lordierclaw.todoserver.database.connector.IDatabaseConnector;
import me.lordierclaw.todoserver.database.mapper.IRowMapper;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao {

    @Inject
    protected IDatabaseConnector dbConnector;

    protected <E> List<E> query(String sql, IRowMapper<E> rowMapper, Object... parameters) {
        List<E> results = new ArrayList<>();
        Connection connection = dbConnector.newConnection();
        if (connection == null) return null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            setParameter(statement, parameters);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.add(rowMapper.mapRow(resultSet));
            }
            return results;
        } catch (SQLException ignored) {
            return null;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                connection.close();
            } catch (SQLException ignored) {
            }
        }
    }

    protected Integer insert(String sql, String primaryKey, Object... parameters) {
        Connection connection = dbConnector.newConnection();
        if (connection == null) return null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer id = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setParameter(statement, parameters);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(primaryKey);
            }
            connection.commit();
            return id;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {
            }
            return null;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                connection.close();
            } catch (SQLException ignored) {
            }
        }
    }

    protected Integer insert(String sql, Object... parameters) {
        return insert(sql, "id", parameters);
    }

    protected void update(String sql, Object... parameters) {
        Connection connection = dbConnector.newConnection();
        if (connection == null) return;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            setParameter(statement, parameters);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {
            }
        } finally {
            try {
                if (statement != null) statement.close();
                connection.close();
            } catch (SQLException ignored) {
            }
        }
    }

    protected void delete(String sql, Object... parameters) {
        update(sql, parameters);
    }

    private void setParameter(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            Object parameter = parameters[i];
            int parameterIndex = i+1;
            if (parameter instanceof Integer) {
                statement.setInt(parameterIndex, (Integer) parameter);
            } else if (parameter instanceof Long) {
                statement.setLong(parameterIndex, (Long) parameter);
            } else if (parameter instanceof String) {
                statement.setString(parameterIndex, (String) parameter);
            } else if (parameter instanceof Boolean) {
                statement.setBoolean(parameterIndex, (Boolean) parameter);
            } else if (parameter instanceof Timestamp) {
                statement.setTimestamp(parameterIndex, (Timestamp) parameter);
            } else {
                throw new RuntimeException("Unsupported SQL Parameter: "+ parameter.getClass().getName());
            }
        }
    }
}


