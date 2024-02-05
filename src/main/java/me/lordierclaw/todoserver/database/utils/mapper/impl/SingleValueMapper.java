package me.lordierclaw.todoserver.database.utils.mapper.impl;

import me.lordierclaw.todoserver.database.utils.mapper.RowMapper;
import me.lordierclaw.todoserver.exception.sql.SQLMappingException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SingleValueMapper<T> implements RowMapper<T> {

    private final Class<T> typeClass;

    public SingleValueMapper(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T mapRow(ResultSet rs) throws SQLMappingException {
        try {
            if (typeClass == String.class) {
                return (T) rs.getString(1);
            } else if (typeClass == Integer.class) {
                return (T) Integer.valueOf(rs.getInt(1));
            } else if (typeClass == Long.class) {
                return (T) Long.valueOf(rs.getLong(1));
            } else if (typeClass == Boolean.class) {
                return (T) Boolean.valueOf(rs.getBoolean(1));
            } else if (typeClass == Timestamp.class) {
                return (T) rs.getTimestamp(1);
            } else {
                throw new SQLMappingException("Unsupported mapping type");
            }
        } catch (SQLException e) {
            throw new SQLMappingException(e);
        }
    }
}
