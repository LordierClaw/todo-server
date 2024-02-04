package me.lordierclaw.todoserver.database.utils.mapper.impl;

import me.lordierclaw.todoserver.database.utils.mapper.IRowMapper;
import me.lordierclaw.todoserver.exception.sql.SQLMappingException;
import me.lordierclaw.todoserver.model.dto.CategoryCountDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryCountMapper implements IRowMapper<CategoryCountDto> {
    @Override
    public CategoryCountDto mapRow(ResultSet rs) throws SQLMappingException {
        try {
            CategoryCountDto categoryCountDto = new CategoryCountDto();
            categoryCountDto.setCategoryName(rs.getString("category_name"));
            categoryCountDto.setTaskCount(rs.getInt("task_count"));
            return categoryCountDto;
        } catch (SQLException e) {
            throw new SQLMappingException(e);
        }
    }
}
