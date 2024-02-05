package me.lordierclaw.todoserver.database.dao;

import me.lordierclaw.todoserver.exception.sql.*;
import me.lordierclaw.todoserver.model.base.Category;

import java.util.List;

public interface CategoryDao {

    boolean isCategoryBelongToUser(int userId, int id) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    int insertCategory(Category category) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    void updateCategory(Category category) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException;

    void deleteCategory(Category category) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException;

    Category getCategory(int userId, int id) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;

    List<Category> getAllCategoriesOfUser(int userId) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException;
}
