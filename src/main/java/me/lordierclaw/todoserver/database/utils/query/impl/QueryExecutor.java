package me.lordierclaw.todoserver.database.utils.query.impl;

import me.lordierclaw.todoserver.database.utils.query.IQueryExecutor;
import me.lordierclaw.todoserver.database.utils.query.OnUpdateResultListener;
import me.lordierclaw.todoserver.database.utils.mapper.IRowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryExecutor implements IQueryExecutor {

    private final Connection connection;

    @Override
    public boolean isAutoCloseConnection() {
        return isAutoCloseConnection;
    }

    @Override
    public boolean close() {
        if (connection != null) {
            try {
                connection.close();
                return true;
            } catch (SQLException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void setAutoCloseConnection(boolean autoCloseConnection) {
        isAutoCloseConnection = autoCloseConnection;
    }

    private void autoCloseConnection() throws SQLException {
        if (isAutoCloseConnection)
            connection.close();
    }

    private boolean isAutoCloseConnection = true;

    private QueryExecutor(Connection connection) {
        this.connection = connection;
    }

    public static QueryExecutor connect(Connection connection) {
        return new QueryExecutor(connection);
    }

    private int[] columnSqlTypes;

    @Override
    public boolean execute(String sql, Object... parameters) {
        if (connection == null) return false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            setParameter(statement, parameters);
            return statement.execute();
        } catch (SQLException e) {
            Logger.getLogger(QueryExecutor.class.getName()).log(Level.SEVERE, e.getMessage());
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
                autoCloseConnection();
            } catch (SQLException ignored) {
            }
        }
    }

    @Override
    public <E> List<E> executeQuery(String sql, IRowMapper<E> rowMapper, Object... parameters) {
        if (connection == null) return null;
        List<E> results = new ArrayList<>();
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
        } catch (SQLException e) {
            Logger.getLogger(QueryExecutor.class.getName()).log(Level.SEVERE, e.getMessage());
            return null;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                autoCloseConnection();
            } catch (SQLException ignored) {
            }
        }
    }

    @Override
    public boolean executeUpdate(String sql, Object... parameters) {
        if (connection == null) return false;
        PreparedStatement statement = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            setParameter(statement, parameters);
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {
            }
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
                autoCloseConnection();
            } catch (SQLException ignored) {
            }
        }
    }

    @Override
    public <T> T executeUpdate(String sql, OnUpdateResultListener<T> listener, Object... parameters) {
        if (connection == null) return null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        T result;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setParameter(statement, parameters);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            result = listener.extractResult(resultSet);
            connection.commit();
            return result;
        } catch (SQLException e) {
            Logger.getLogger(QueryExecutor.class.getName()).log(Level.SEVERE, e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ignored) {
            }
            return null;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                autoCloseConnection();
            } catch (SQLException ignored) {
            }
        }
    }

    @Override
    public IQueryExecutor setSQLTypes(int... sqlTypes) {
        columnSqlTypes = sqlTypes;
        return this;
    }

    private void setParameter(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            Object parameter = parameters[i];
            int parameterIndex = i+1;
            if (parameter == null) {
                statement.setNull(parameterIndex, columnSqlTypes[i]);
                continue;
            }
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
