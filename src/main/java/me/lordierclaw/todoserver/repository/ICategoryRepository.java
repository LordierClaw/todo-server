package me.lordierclaw.todoserver.repository;

import me.lordierclaw.todoserver.model.base.Category;

import java.util.List;

public interface ICategoryRepository {
    int insertCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(Category category);
    Category getCategory(int id);
    List<Category> getAllCategoriesOfUser(int userId);
}
