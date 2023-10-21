package me.lordierclaw.todoserver.database.utils.mapper.impl;

import me.lordierclaw.todoserver.database.utils.mapper.IRowMapper;
import me.lordierclaw.todoserver.model.client.CategoryCount;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryCountMapper implements IRowMapper<CategoryCount> {
    @Override
    public CategoryCount mapRow(ResultSet rs) {
        try {
            CategoryCount categoryCount = new CategoryCount();
            categoryCount.setCategoryName(rs.getString("category_name"));
            categoryCount.setTaskCount(rs.getInt("task_count"));
            return categoryCount;
        } catch (SQLException e) {
            return null;
        }
    }
}
