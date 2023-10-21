package me.lordierclaw.todoserver.database.dao.impl;

import me.lordierclaw.todoserver.database.dao.AbstractDao;
import me.lordierclaw.todoserver.database.dao.ITaskDao;
import me.lordierclaw.todoserver.database.utils.mapper.impl.CategoryCountMapper;
import me.lordierclaw.todoserver.database.utils.mapper.impl.TaskMapper;
import me.lordierclaw.todoserver.database.utils.query.IQueryExecutorBuilder;
import me.lordierclaw.todoserver.model.base.Task;
import me.lordierclaw.todoserver.model.client.CategoryCount;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

public class TaskDao extends AbstractDao implements ITaskDao {
    public TaskDao(IQueryExecutorBuilder executorBuilder) {
        super(executorBuilder);
    }

    @Override
    public Integer insertTask(Task task) {
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
    public Boolean updateTask(Task task) {
        String sql = "UPDATE task SET status = ?, name = ?, category_id = ?, " +
                "due_date = ?, reminder_at = ?, repeat_type = ?, notes = ? WHERE id = ? AND user_id = ?";
        return prepareExecutorWithTypes(
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
    public Boolean deleteTask(Task task) {
        String sql = "DELETE FROM task WHERE id = ? AND user_id = ?";
        return prepareExecutor().delete(sql, task.getId(), task.getUserId());
    }

    @Override
    public Task getTask(int userId, int id) {
        String sql = "SELECT * FROM task WHERE id = ? AND user_id = ?";
        List<Task> result = prepareExecutor().query(sql, new TaskMapper(), id, userId);
        if (result == null || result.isEmpty()) return null;
        return result.get(0);
    }

    @Override
    public List<Task> getAllTaskOfUser(int userId) {
        String sql = "SELECT * FROM task WHERE user_id = ?";
        return prepareExecutor().query(sql, new TaskMapper(), userId);
    }

    @Override
    public List<Task> getAllTaskInCategory(int user_id, int categoryId) {
        String sql = "SELECT * FROM task WHERE category_id = ? AND user_id = ?";
        return prepareExecutor().query(sql, new TaskMapper(), categoryId, user_id);
    }

    @Override
    public List<Task> getAllTaskOfUserContainsTitle(int userId, String keyword) {
        String sql = "SELECT * FROM task WHERE user_id = ?, WHERE name LIKE '%' || ? || '%'";
        return prepareExecutor().query(sql, new TaskMapper(), userId, keyword);
    }

    @Override
    public List<Task> getAllTaskOfUserInRangeTime(int userId, Timestamp startTime, Timestamp endTime) {
        String sql = "SELECT * FROM task WHERE user_id = ? AND due_date BETWEEN ? AND ?";
        return prepareExecutor().query(sql, new TaskMapper(), userId, startTime, endTime);
    }

    @Override
    public List<Task> getTaskCountByStatusOfUser(int userId, boolean status) {
        String sql = "COUNT(*) FROM task WHERE user_id = ? AND status = ?";
        return prepareExecutor().query(sql, new TaskMapper(), userId, status);
    }

    @Override
    public List<CategoryCount> getCategoryCountsOfUser(int userId) {
        String sql =
                "SELECT category_user.name AS category_name, COUNT(task.id) AS task_count " +
                "FROM" +
                "   (SELECT * FROM category WHERE user_id = ?) AS category_user " +
                "   LEFT JOIN task ON category_user.id = task.category_id AND category_user.user_id = task.user_id " +
                "GROUP BY category_name";
        return prepareExecutor().query(sql, new CategoryCountMapper(), userId);
    }
}
