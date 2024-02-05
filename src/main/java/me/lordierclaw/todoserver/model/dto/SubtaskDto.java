package me.lordierclaw.todoserver.model.dto;

import me.lordierclaw.todoserver.model.base.Identifiable;
import me.lordierclaw.todoserver.model.base.Subtask;

public class SubtaskDto extends Identifiable {
    private boolean status;
    private String name;
    private int taskId;

    public static SubtaskDto fromSubtask(Subtask subtask) {
        SubtaskDto subtaskDto = new SubtaskDto();
        subtaskDto.setId(subtask.getId());
        subtaskDto.setStatus(subtask.getStatus());
        subtaskDto.setName(subtask.getName());
        subtaskDto.setTaskId(subtask.getTaskId());
        return subtaskDto;
    }

    public Subtask toSubtask() {
        Subtask subtask = new Subtask();
        subtask.setId(id);
        subtask.setName(name);
        subtask.setStatus(status);
        subtask.setTaskId(taskId);
        return subtask;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
