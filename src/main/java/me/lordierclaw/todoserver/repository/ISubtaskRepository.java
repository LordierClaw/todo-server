package me.lordierclaw.todoserver.repository;

import me.lordierclaw.todoserver.model.base.Subtask;

import java.util.List;

public interface ISubtaskRepository {
    int insertSubtask(Subtask subtask);
    void updateSubtask(Subtask subtask);
    void deleteSubtask(Subtask subtask);
    Subtask getSubtask(int id);
    List<Subtask> getAllSubtaskOfTask(int taskId);
}
