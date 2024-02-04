package me.lordierclaw.todoserver.database.utils.query.impl;

import me.lordierclaw.todoserver.database.connector.DatabaseConnector;
import me.lordierclaw.todoserver.database.utils.mapper.RowMapper;
import me.lordierclaw.todoserver.database.utils.query.QueryExecutor;
import me.lordierclaw.todoserver.database.utils.query.OnUpdateResultListener;
import me.lordierclaw.todoserver.exception.sql.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QueryExecutorImpl implements QueryExecutor {

    private final DatabaseConnector dbConnector;

    private QueryExecutorImpl(DatabaseConnector dbConnector) {
        this.dbConnector = Objects.requireNonNull(dbConnector);
    }

    public static QueryExecutorImpl connect(DatabaseConnector dbConnector) {
        return new QueryExecutorImpl(dbConnector);
    }

    private int[] columnSqlTypes;

    @Override
    public void execute(String sql, Object... parameters) throws SQLConnectException, SQLTypeException, SQLQueryException {
        try (Connection connection = dbConnector.newConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setParameter(statement, parameters);
                statement.execute();
            } catch (SQLException e) {
                throw new SQLQueryException(e);
            }
        } catch (SQLException e) {
            throw new SQLConnectException(e);
        }
    }

    @Override
    public <E> List<E> executeQuery(String sql, RowMapper<E> rowMapper, Object... parameters) throws SQLConnectException, SQLTypeException, SQLQueryException, SQLMappingException {
        List<E> results = new ArrayList<>();
        try (Connection connection = dbConnector.newConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setParameter(statement, parameters);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        results.add(rowMapper.mapRow(resultSet));
                    }
                    return results;
                } catch (SQLException e) {
                    throw new SQLQueryException(e);
                }
            }
        } catch (SQLException e) {
            throw new SQLConnectException(e);
        }
    }

    @Override
    public void executeUpdate(String sql, Object... parameters) throws SQLConnectException, SQLTypeException, SQLQueryException, SQLRollbackException {
        try (Connection connection = dbConnector.newConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                setParameter(statement, parameters);
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                    throw new SQLQueryException(e);
                } catch (SQLException commitException) {
                    throw new SQLRollbackException(e);
                }
            }
        } catch (SQLException e) {
            throw new SQLConnectException(e);
        }
    }

    @Override
    public <T> T executeUpdate(String sql, OnUpdateResultListener<T> listener, Object... parameters) throws SQLConnectException, SQLTypeException, SQLQueryException, SQLRollbackException, SQLMappingException {
        try (Connection connection = dbConnector.newConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                setParameter(statement, parameters);
                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();
                T result;
                try {
                    result = listener.extractResult(resultSet);
                } catch (SQLException e) {
                    throw new SQLMappingException(e);
                }
                connection.commit();
                return result;
            } catch (SQLException e) {
                try {
                    connection.rollback();
                    throw new SQLQueryException(e);
                } catch (SQLException commitException) {
                    throw new SQLRollbackException(e);
                }
            }
        } catch (SQLException e) {
            throw new SQLConnectException(e);
        }
    }

    @Override
    public QueryExecutor setSQLTypes(int... sqlTypes) {
        columnSqlTypes = sqlTypes;
        return this;
    }

    private void setParameter(PreparedStatement statement, Object... parameters) throws SQLTypeException {
        try {
            for (int i = 0; i < parameters.length; i++) {
                Object parameter = parameters[i];
                int parameterIndex = i + 1;
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
                    throw new SQLTypeException("Unsupported SQL Parameter: " + parameter.getClass().getName());
                }
            }
        } catch (SQLException e) {
            throw new SQLTypeException("Unable to set parameter: ", e);
        }
    }
}
