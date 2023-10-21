package me.lordierclaw.todoserver.database.utils.query;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface OnUpdateResultListener<T> {
    T extractResult(ResultSet resultSet) throws SQLException;
}
