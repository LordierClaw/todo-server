package me.lordierclaw.todoserver.repository.impl;

import me.lordierclaw.todoserver.database.instance.AbstractDatabaseInstance;
import me.lordierclaw.todoserver.exception.data.DataCrudException;
import me.lordierclaw.todoserver.exception.data.DataInvalidateException;
import me.lordierclaw.todoserver.exception.sql.*;
import me.lordierclaw.todoserver.model.base.Category;
import me.lordierclaw.todoserver.repository.AbstractRepository;
import me.lordierclaw.todoserver.repository.ICategoryRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryRepository extends AbstractRepository implements ICategoryRepository {
    @Inject
    private AbstractDatabaseInstance databaseInstance;

    @Override
    public int insertCategory(Category category) throws DataCrudException, DataInvalidateException {
        try {
            int result = databaseInstance.getCategoryDao().insertCategory(category);
            invalidate();
            return result;
        } catch (SQLRollbackException e) {
            Logger.getLogger(TaskRepository.class.getName()).log(Level.SEVERE, "Unable to rollback changes!", e);
            throw new DataCrudException(e);
        } catch (SQLMappingException | SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public void updateCategory(Category category) throws DataCrudException, DataInvalidateException {
        try {
            databaseInstance.getCategoryDao().updateCategory(category);
            invalidate();
        } catch (SQLRollbackException e) {
            Logger.getLogger(TaskRepository.class.getName()).log(Level.SEVERE, "Unable to rollback changes!", e);
            throw new DataCrudException(e);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public void deleteCategory(Category category) throws DataCrudException, DataInvalidateException {
        try {
            databaseInstance.getCategoryDao().deleteCategory(category);
            invalidate();
        } catch (SQLRollbackException e) {
            Logger.getLogger(TaskRepository.class.getName()).log(Level.SEVERE, "Unable to rollback changes!", e);
            throw new DataCrudException(e);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public Category getCategory(int userId, int id) throws DataCrudException {
        try {
            return databaseInstance.getCategoryDao().getCategory(userId, id);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException | SQLMappingException e) {
            throw new DataCrudException(e);
        }
    }

    @Override
    public List<Category> getAllCategoriesOfUser(int userId) throws DataCrudException {
        try {
            return databaseInstance.getCategoryDao().getAllCategoriesOfUser(userId);
        } catch (SQLQueryException | SQLTypeException | SQLConnectException | SQLMappingException e) {
            throw new DataCrudException(e);
        }
    }
}
