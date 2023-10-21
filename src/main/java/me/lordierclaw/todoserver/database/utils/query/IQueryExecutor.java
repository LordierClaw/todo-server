package me.lordierclaw.todoserver.database.utils.query;

import me.lordierclaw.todoserver.database.utils.mapper.IRowMapper;

import java.util.List;

public interface IQueryExecutor {

    boolean execute(String sql, Object...parameters);
    <E> List<E> executeQuery(String sql, IRowMapper<E> rowMapper, Object... parameters);
    boolean executeUpdate(String sql, Object... parameters);
    <T> T executeUpdate(String sql, OnUpdateResultListener<T> listener, Object... parameters);
    IQueryExecutor setSQLTypes(int... sqlTypes);
    void setAutoCloseConnection(boolean value);
    boolean isAutoCloseConnection();
    boolean close();
}
