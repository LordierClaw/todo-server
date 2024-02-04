package me.lordierclaw.todoserver.database.dao.impl;

import me.lordierclaw.todoserver.database.dao.AbstractDao;
import me.lordierclaw.todoserver.database.dao.TaskDao;
import me.lordierclaw.todoserver.database.utils.mapper.impl.CategoryCountMapper;
import me.lordierclaw.todoserver.database.utils.mapper.impl.TaskMapper;
import me.lordierclaw.todoserver.database.utils.query.QueryExecutorBuilder;
import me.lordierclaw.todoserver.exception.sql.*;
import me.lordierclaw.todoserver.model.base.Task;
import me.lordierclaw.todoserver.model.dto.CategoryCountDto;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

public class TaskDaoImpl extends AbstractDao implements TaskDao {
    public TaskDaoImpl(QueryExecutorBuilder executorBuilder) {
        super(executorBuilder);
    }

    @Override
    public int insertTask(Task task) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "INSERT INTO task (status, name, user_id, category_id, due_date, reminder_at, repeat_type, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return prepareExecutorWithTypes(
                Types.BOOLEAN,
                Types.VARCHAR,
                Types.INTEGER,
                Types.INTEGER,
                Types.TIMESTAMP,
                Types.INTEGER,
                Types.VARCHAR,
                Types.VARCHAR
        )
                .insert(sql,
                        task.getStatus(),
                        task.getName(),
                        task.getUserId(),
                        task.getCategoryId(),
                        task.getDueDate(),
                        task.getReminderAt(),
                        task.getRepeatType(),
                        task.getNotes()
                );
    }

    @Override
    public void updateTask(Task task) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException {
        String sql = "UPDATE task SET status = ?, name = ?, category_id = ?, " +
                "due_date = ?, reminder_at = ?, repeat_type = ?, notes = ? WHERE id = ? AND user_id = ?";
        prepareExecutorWithTypes(
                Types.BOOLEAN,
                Types.VARCHAR,
                Types.INTEGER,
                Types.TIMESTAMP,
                Types.INTEGER,
                Types.VARCHAR,
                Types.VARCHAR
        ).update(sql,
                task.getStatus(),
                task.getName(),
                task.getCategoryId(),
                task.getDueDate(),
                task.getReminderAt(),
                task.getRepeatType(),
                task.getNotes(),
                task.getId(),
                task.getUserId()
        );
    }

    @Override
    public void deleteTask(Task task) throws SQLRollbackException, SQLQueryException, SQLTypeException, SQLConnectException {
        String sql = "DELETE FROM task WHERE id = ? AND user_id = ?";
        prepareExecutor().delete(sql, task.getId(), task.getUserId());
    }

    @Override
    public Task getTask(int userId, int id) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "SELECT * FROM task WHERE id = ? AND user_id = ?";
        List<Task> result = prepareExecutor().query(sql, new TaskMapper(), id, userId);
        if (result == null || result.isEmpty()) return null;
        return result.get(0);
    }

    @Override
    public List<Task> getAllTaskOfUser(int userId) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "SELECT * FROM task WHERE user_id = ?";
        return prepareExecutor().query(sql, new TaskMapper(), userId);
    }

    @Override
    public List<Task> getAllTaskInCategory(int user_id, int categoryId) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "SELECT * FROM task WHERE category_id = ? AND user_id = ?";
        return prepareExecutor().query(sql, new TaskMapper(), categoryId, user_id);
    }

    @Override
    public List<Task> getAllTaskOfUserContainsTitle(int userId, String keyword) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "SELECT * FROM task WHERE user_id = ?, WHERE name LIKE '%' || ? || '%'";
        return prepareExecutor().query(sql, new TaskMapper(), userId, keyword);
    }

    @Override
    public List<Task> getAllTaskOfUserInRangeTime(int userId, Timestamp startTime, Timestamp endTime) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "SELECT * FROM task WHERE user_id = ? AND due_date BETWEEN ? AND ?";
        return prepareExecutor().query(sql, new TaskMapper(), userId, startTime, endTime);
    }

    @Override
    public List<Task> getTaskCountByStatusOfUser(int userId, boolean status) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql = "COUNT(*) FROM task WHERE user_id = ? AND status = ?";
        return prepareExecutor().query(sql, new TaskMapper(), userId, status);
    }

    @Override
    public List<CategoryCountDto> getCategoryCountsOfUser(int userId) throws SQLQueryException, SQLTypeException, SQLConnectException, SQLMappingException {
        String sql =
                "SELECT category_user.name AS category_name, COUNT(task.id) AS task_count " +
                        "FROM" +
                        "   (SELECT * FROM category WHERE user_id = ?) AS category_user " +
                        "   LEFT JOIN task ON category_user.id = task.category_id AND category_user.user_id = task.user_id " +
                        "GROUP BY category_name";
        return prepareExecutor().query(sql, new CategoryCountMapper(), userId);
    }
}
