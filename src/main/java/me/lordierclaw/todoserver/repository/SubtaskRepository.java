package me.lordierclaw.todoserver.repository;

import me.lordierclaw.todoserver.exception.data.DataCrudException;
import me.lordierclaw.todoserver.exception.data.DataInvalidateException;
import me.lordierclaw.todoserver.model.base.Subtask;

import java.util.List;

public interface SubtaskRepository {
    int insertSubtask(Subtask subtask) throws DataCrudException, DataInvalidateException;

    void updateSubtask(Subtask subtask) throws DataCrudException, DataInvalidateException;

    void deleteSubtask(Subtask subtask) throws DataCrudException, DataInvalidateException;

    Subtask getSubtask(int id) throws DataCrudException;

    List<Subtask> getAllSubtaskOfTask(int taskId) throws DataCrudException;
}
