package me.lordierclaw.todoserver.database.dao.impl;

import me.lordierclaw.todoserver.database.dao.AbstractDao;
import me.lordierclaw.todoserver.database.dao.ICategoryDao;
import me.lordierclaw.todoserver.database.mapper.impl.CategoryMapper;
import me.lordierclaw.todoserver.model.base.Category;

import java.util.List;

public class CategoryDao extends AbstractDao implements ICategoryDao {
    @Override
    public int insertCategory(Category category) {
        String sql = "INSERT INTO category (name, user_id) VALUES (?, ?)";
        return insert(sql, category.getName(), category.getUserId());
    }

    @Override
    public void updateCategory(Category category) {
        String sql = "UPDATE category SET name = ? WHERE id = ?";
        update(sql, category.getName(), category.getId());
    }

    @Override
    public void deleteCategory(Category category) {
        String sql = "DELETE FROM category WHERE id = ?";
        delete(sql, category.getId());
    }

    @Override
    public Category getCategory(int id) {
        String sql = "SELECT * FROM category WHERE id = ?";
        List<Category> result = query(sql, new CategoryMapper(), id);
        if (result == null || result.isEmpty()) return null;
        return result.get(0);
    }

    @Override
    public List<Category> getAllCategoriesOfUser(int userId) {
        String sql = "SELECT * FROM category WHERE user_id = ?";
        return query(sql, new CategoryMapper(), userId);
    }
}
