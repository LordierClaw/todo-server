package me.lordierclaw.todoserver.database.mapper.impl;

import me.lordierclaw.todoserver.database.mapper.IRowMapper;
import me.lordierclaw.todoserver.model.base.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements IRowMapper<User> {
    @Override
    public User mapRow(ResultSet rs) {
        try {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        } catch (SQLException e) {
            return null;
        }
    }
}
