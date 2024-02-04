package me.lordierclaw.todoserver.database.utils.query;

import me.lordierclaw.todoserver.database.utils.mapper.IRowMapper;
import me.lordierclaw.todoserver.exception.sql.*;

import java.util.List;

public abstract class PreparedExecutor {
    protected abstract IQueryExecutor buildExecutor();

    public <E> List<E> query(String sql, IRowMapper<E> rowMapper, Object... parameters) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        return buildExecutor().executeQuery(sql, rowMapper, parameters);
    }

    public int insert(String sql, String primaryKey, Object... parameters) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        return buildExecutor().executeUpdate(sql, resultSet -> {
            resultSet.next();
            return resultSet.getInt(1);
        }, parameters);
    }

    public int insert(String sql, Object... parameters) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        return insert(sql, "id", parameters);
    }

    public void update(String sql, Object... parameters) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException {
        buildExecutor().executeUpdate(sql, parameters);
    }

    public void delete(String sql, Object... parameters) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException {
        update(sql, parameters);
    }
}