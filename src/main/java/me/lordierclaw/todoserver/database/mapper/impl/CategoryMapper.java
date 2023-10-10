package me.lordierclaw.todoserver.database.mapper.impl;

import me.lordierclaw.todoserver.database.mapper.IRowMapper;
import me.lordierclaw.todoserver.model.base.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements IRowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs) {
        try {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            category.setUserId(rs.getInt("user_id"));
            return category;
        } catch (SQLException e) {
            return null;
        }
    }
}
