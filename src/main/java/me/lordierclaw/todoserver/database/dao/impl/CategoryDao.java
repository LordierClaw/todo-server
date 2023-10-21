package me.lordierclaw.todoserver.database.dao.impl;

import me.lordierclaw.todoserver.database.dao.AbstractDao;
import me.lordierclaw.todoserver.database.dao.ICategoryDao;
import me.lordierclaw.todoserver.database.utils.mapper.impl.CategoryMapper;
import me.lordierclaw.todoserver.database.utils.query.IQueryExecutorBuilder;
import me.lordierclaw.todoserver.model.base.Category;

import java.util.List;

public class CategoryDao extends AbstractDao implements ICategoryDao {
    public CategoryDao(IQueryExecutorBuilder executorBuilder) {
        super(executorBuilder);
    }

    @Override
    public Integer insertCategory(Category category) {
        String sql = "INSERT INTO category (name, user_id) VALUES (?, ?)";
        return prepareExecutor().insert(sql, category.getName(), category.getUserId());
    }

    @Override
    public Boolean updateCategory(Category category) {
        String sql = "UPDATE category SET name = ? WHERE id = ?";
        return prepareExecutor().update(sql, category.getName(), category.getId());
    }

    @Override
    public Boolean deleteCategory(Category category) {
        String sql = "DELETE FROM category WHERE id = ?";
        return prepareExecutor().delete(sql, category.getId());
    }

    @Override
    public Category getCategory(int userId, int id) {
        String sql = "SELECT * FROM category WHERE id = ? AND user_id = ?";
        List<Category> result = prepareExecutor().query(sql, new CategoryMapper(), id, userId);
        if (result == null || result.isEmpty()) return null;
        return result.get(0);
    }

    @Override
    public List<Category> getAllCategoriesOfUser(int userId) {
        String sql = "SELECT * FROM category WHERE user_id = ?";
        return prepareExecutor().query(sql, new CategoryMapper(), userId);
    }
}
