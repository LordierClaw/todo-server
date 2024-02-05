package me.lordierclaw.todoserver.repository;

import me.lordierclaw.todoserver.exception.data.DataCrudException;
import me.lordierclaw.todoserver.exception.data.DataInvalidateException;
import me.lordierclaw.todoserver.model.base.Category;

import java.util.List;

public interface CategoryRepository {
    boolean isCategoryBelongToUser(int userId, int id) throws DataCrudException;

    int insertCategory(Category category) throws DataCrudException, DataInvalidateException;

    void updateCategory(Category category) throws DataCrudException, DataInvalidateException;

    void deleteCategory(Category category) throws DataCrudException, DataInvalidateException;

    Category getCategory(int user, int id) throws DataCrudException;

    List<Category> getAllCategoriesOfUser(int userId) throws DataCrudException;
}
