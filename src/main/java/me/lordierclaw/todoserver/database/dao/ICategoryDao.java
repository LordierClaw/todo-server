package me.lordierclaw.todoserver.database.dao;

import me.lordierclaw.todoserver.model.base.Category;

import java.util.List;

public interface ICategoryDao {
    Integer insertCategory(Category category);
    Boolean updateCategory(Category category);
    Boolean deleteCategory(Category category);
    Category getCategory(int userId, int id);
    List<Category> getAllCategoriesOfUser(int userId);
}
