package me.lordierclaw.todoserver.repository;

import me.lordierclaw.todoserver.model.base.Category;

import java.util.List;

public interface ICategoryRepository {
    int insertCategory(Category category);
    boolean updateCategory(Category category);
    boolean deleteCategory(Category category);
    Category getCategory(int user, int id);
    List<Category> getAllCategoriesOfUser(int userId);
}
