package me.lordierclaw.todoserver.database.utils.mapper;

import me.lordierclaw.todoserver.exception.sql.SQLMappingException;

import java.sql.ResultSet;

public interface IRowMapper<T> {
    T mapRow(ResultSet rs) throws SQLMappingException;
}
