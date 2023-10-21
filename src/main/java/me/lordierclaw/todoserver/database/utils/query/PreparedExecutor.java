package me.lordierclaw.todoserver.database.utils.query;

import me.lordierclaw.todoserver.database.utils.mapper.IRowMapper;

import java.util.List;

public abstract class PreparedExecutor {
    protected abstract IQueryExecutor buildExecutor();

    public <E> List<E> query(String sql, IRowMapper<E> rowMapper, Object... parameters) {
        return buildExecutor().executeQuery(sql, rowMapper, parameters);
    }

    public Integer insert(String sql, String primaryKey, Object... parameters) {
        return buildExecutor().executeUpdate(sql, resultSet -> {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return null;
            }
        }, parameters);
    }

    public Integer insert(String sql, Object... parameters) {
        return insert(sql, "id", parameters);
    }

    public boolean update(String sql, Object... parameters) {
        return buildExecutor().executeUpdate(sql, parameters);
    }

    public boolean delete(String sql, Object... parameters) {
        return update(sql, parameters);
    }
}