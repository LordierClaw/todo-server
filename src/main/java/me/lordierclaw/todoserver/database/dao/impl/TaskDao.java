package me.lordierclaw.todoserver.database.dao.impl;

import me.lordierclaw.todoserver.database.dao.AbstractDao;
import me.lordierclaw.todoserver.database.dao.ITaskDao;
import me.lordierclaw.todoserver.database.mapper.impl.CategoryCountMapper;
import me.lordierclaw.todoserver.database.mapper.impl.TaskMapper;
import me.lordierclaw.todoserver.model.client.CategoryCount;
import me.lordierclaw.todoserver.model.base.Task;

import java.sql.Timestamp;
import java.util.List;

public class TaskDao extends AbstractDao implements ITaskDao {
    @Override
    public int insertTask(Task task) {
        String sql = "INSERT INTO task (status, name, user_id, category_id, due_date, reminder_at, repeat_type, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return insert(sql,
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
    public void updateTask(Task task) {
        String sql = "UPDATE task SET status = ?, name = ?, user_id = ?, category_id = ?, " +
                "due_date = ?, reminder_at = ?, repeat_type = ?, notes = ? WHERE id = ?";
        update(sql,
                task.getStatus(),
                task.getName(),
                task.getUserId(),
                task.getCategoryId(),
                task.getDueDate(),
                task.getReminderAt(),
                task.getRepeatType(),
                task.getNotes(),
                task.getId()
        );
    }

    @Override
    public void deleteTask(Task task) {
        String sql = "DELETE FROM task WHERE id = ?";
        delete(sql, task.getId());
    }

    @Override
    public void deleteTask(int id) {
        String sql = "DELETE FROM task WHERE id = ?";
        delete(sql, id);
    }

    @Override
    public Task getTask(int id) {
        String sql = "SELECT * FROM task WHERE id = ?";
        List<Task> result = query(sql, new TaskMapper(), id);
        if (result == null || result.isEmpty()) return null;
        return result.get(0);
    }

    @Override
    public List<Task> getAllTaskOfUser(int userId) {
        String sql = "SELECT * FROM task WHERE user_id = ?";
        return query(sql, new TaskMapper(), userId);
    }

    @Override
    public List<Task> getAllTaskInCategory(int categoryId) {
        String sql = "SELECT * FROM task WHERE category_id = ?";
        return query(sql, new TaskMapper(), categoryId);
    }

    @Override
    public List<Task> getAllTaskOfUserContainsTitle(int userId, String keyword) {
        String sql = "SELECT * FROM task WHERE user_id = ?, WHERE name LIKE '%' || ? || '%'";
        return query(sql, new TaskMapper(), userId, keyword);
    }

    @Override
    public List<Task> getAllTaskOfUserInRangeTime(int userId, Timestamp startTime, Timestamp endTime) {
        String sql = "SELECT * FROM task WHERE user_id = ?, due_date BETWEEN ? AND ?";
        return query(sql, new TaskMapper(), userId, startTime, endTime);
    }

    @Override
    public List<Task> getTaskCountByStatusOfUser(int userId, boolean status) {
        String sql = "COUNT(*) FROM task WHERE user_id = ?, status = ?";
        return query(sql, new TaskMapper(), userId, status);
    }

    @Override
    public List<CategoryCount> getCategoryCountsOfUser(int userId) {
        String sql =
                "SELECT category_user.name AS category_name, COUNT(task.id) AS task_count " +
                "FROM" +
                "   (SELECT * FROM category WHERE user_id = ?) AS category_user " +
                "   LEFT JOIN task ON category_user.id = task.category_id AND category_user.user_id = task.user_id " +
                "GROUP BY category_name";
        return query(sql, new CategoryCountMapper(), userId);
    }
}
