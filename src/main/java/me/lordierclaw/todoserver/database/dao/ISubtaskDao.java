package me.lordierclaw.todoserver.database.dao;

import me.lordierclaw.todoserver.model.base.Subtask;

import java.util.List;

public interface ISubtaskDao {
    Integer insertSubtask(Subtask subtask);
    Boolean updateSubtask(Subtask subtask);
    Boolean deleteSubtask(Subtask subtask);
    Subtask getSubtask(int id);
    List<Subtask> getAllSubtaskOfTask(int taskId);
}
