package me.lordierclaw.todoserver.database.utils.mapper.impl;

import me.lordierclaw.todoserver.database.utils.mapper.RowMapper;
import me.lordierclaw.todoserver.exception.sql.SQLMappingException;
import me.lordierclaw.todoserver.model.base.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs) throws SQLMappingException {
        try {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            category.setUserId(rs.getInt("user_id"));
            return category;
        } catch (SQLException e) {
            throw new SQLMappingException(e);
        }
    }
}
