package me.lordierclaw.todoserver.repository;

import me.lordierclaw.todoserver.model.base.Subtask;

import java.util.List;

public interface ISubtaskRepository {
    int insertSubtask(Subtask subtask);
    boolean updateSubtask(Subtask subtask);
    boolean deleteSubtask(Subtask subtask);
    Subtask getSubtask(int id);
    List<Subtask> getAllSubtaskOfTask(int taskId);
}
