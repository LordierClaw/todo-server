package me.lordierclaw.todoserver.database.utils.query;

import me.lordierclaw.todoserver.database.utils.mapper.IRowMapper;
import me.lordierclaw.todoserver.exception.sql.*;

import java.util.List;

public interface IQueryExecutor {
    void execute(String sql, Object... parameters) throws SQLConnectException, SQLTypeException, SQLQueryException;

    <E> List<E> executeQuery(String sql, IRowMapper<E> rowMapper, Object... parameters) throws SQLConnectException, SQLTypeException, SQLQueryException, SQLMappingException;

    void executeUpdate(String sql, Object... parameters) throws SQLConnectException, SQLTypeException, SQLQueryException, SQLRollbackException;

    <T> T executeUpdate(String sql, OnUpdateResultListener<T> listener, Object... parameters) throws SQLConnectException, SQLTypeException, SQLQueryException, SQLRollbackException, SQLMappingException;

    IQueryExecutor setSQLTypes(int... sqlTypes);
}
