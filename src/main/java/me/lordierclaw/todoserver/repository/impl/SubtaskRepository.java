package me.lordierclaw.todoserver.repository.impl;

import me.lordierclaw.todoserver.database.instance.AbstractDatabaseInstance;
import me.lordierclaw.todoserver.model.base.Subtask;
import me.lordierclaw.todoserver.repository.ISubtaskRepository;

import javax.inject.Inject;
import java.util.List;

public class SubtaskRepository implements ISubtaskRepository {
    @Inject
    private AbstractDatabaseInstance databaseInstance;

    @Override
    public int insertSubtask(Subtask subtask) {
        return databaseInstance.getSubtaskDao().insertSubtask(subtask);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        databaseInstance.getSubtaskDao().updateSubtask(subtask);
    }

    @Override
    public void deleteSubtask(Subtask subtask) {
        databaseInstance.getSubtaskDao().deleteSubtask(subtask);
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
