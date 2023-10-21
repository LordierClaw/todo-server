package me.lordierclaw.todoserver.database.utils.mapper;

import java.sql.ResultSet;

public interface IRowMapper<T> {
    T mapRow(ResultSet rs);
}
