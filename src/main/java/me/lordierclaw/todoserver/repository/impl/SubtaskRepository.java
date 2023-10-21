package me.lordierclaw.todoserver.repository.impl;

import me.lordierclaw.todoserver.model.base.Subtask;
import me.lordierclaw.todoserver.repository.AbstractRepository;
import me.lordierclaw.todoserver.repository.ISubtaskRepository;

import java.util.List;

public class SubtaskRepository extends AbstractRepository implements ISubtaskRepository {
    @Override
    public int insertSubtask(Subtask subtask) {
        return databaseInstance.getSubtaskDao().insertSubtask(subtask);
    }

    @Override
    public boolean updateSubtask(Subtask subtask) {
        return databaseInstance.getSubtaskDao().updateSubtask(subtask);
    }

    @Override
    public boolean deleteSubtask(Subtask subtask) {
        return databaseInstance.getSubtaskDao().deleteSubtask(subtask);
    }

    @Override
    public Subtask getSubtask(int id) {
        return databaseInstance.getSubtaskDao().getSubtask(id);
    }

    @Override
    public List<Subtask> getAllSubtaskOfTask(int taskId) {
        return databaseInstance.getSubtaskDao().getAllSubtaskOfTask(taskId);
    }
}
