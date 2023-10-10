package me.lordierclaw.todoserver.repository.impl;

import me.lordierclaw.todoserver.database.instance.AbstractDatabaseInstance;
import me.lordierclaw.todoserver.model.base.Category;
import me.lordierclaw.todoserver.repository.ICategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryRepository implements ICategoryRepository {

    @Inject
    private AbstractDatabaseInstance databaseInstance;

    @Override
    public int insertCategory(Category category) {
        return databaseInstance.getCategoryDao().insertCategory(category);
    }

    @Override
    public void updateCategory(Category category) {
        databaseInstance.getCategoryDao().updateCategory(category);
    }

    @Override
    public void deleteCategory(Category category) {
        databaseInstance.getCategoryDao().deleteCategory(category);
    }

    @Override
    public Category getCategory(int id) {
        return databaseInstance.getCategoryDao().getCategory(id);
    }

    @Override
    public List<Category> getAllCategoriesOfUser(int userId) {
        return databaseInstance.getCategoryDao().getAllCategoriesOfUser(userId);
    }
}
