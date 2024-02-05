package me.lordierclaw.todoserver.service.impl;

import me.lordierclaw.todoserver.exception.data.DataCrudException;
import me.lordierclaw.todoserver.exception.data.DataInvalidateException;
import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.exception.response.ResponseValue;
import me.lordierclaw.todoserver.model.base.Subtask;
import me.lordierclaw.todoserver.model.dto.SubtaskDto;
import me.lordierclaw.todoserver.repository.SubtaskRepository;
import me.lordierclaw.todoserver.repository.TaskRepository;
import me.lordierclaw.todoserver.service.AuthorizedService;
import me.lordierclaw.todoserver.service.SubtaskService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class SubtaskServiceImpl extends AuthorizedService implements SubtaskService {

    @Inject
    private SubtaskRepository subtaskRepository;
    @Inject
    private TaskRepository taskRepository;

    private List<SubtaskDto> mapDtoList(List<Subtask> subtasks) throws ResponseException {
        if (subtasks == null) throw new ResponseException(ResponseValue.ITEM_NOT_FOUND);
        List<SubtaskDto> results = new ArrayList<>();
        for (Subtask subtask : subtasks) {
            results.add(SubtaskDto.fromSubtask(subtask));
        }
        return results;
    }

    @Override
    public int insertSubtask(String token, SubtaskDto subtaskDto) throws ResponseException {
        Subtask subtask = subtaskDto.toSubtask();
        try {
            if (!taskRepository.isTaskBelongToUser(authorizeUser(token), subtask.getTaskId())) {
                throw new ResponseException(ResponseValue.ACCESS_DENIED);
            }
            return subtaskRepository.insertSubtask(subtask);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        } catch (DataInvalidateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateSubtask(String token, SubtaskDto subtaskDto) throws ResponseException {
        Subtask subtask = subtaskDto.toSubtask();
        try {
            if (!subtaskRepository.isSubtaskBelongToUser(authorizeUser(token), subtask.getTaskId(), subtask.getId())) {
                throw new ResponseException(ResponseValue.ACCESS_DENIED);
            }
            subtaskRepository.updateSubtask(subtask);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        } catch (DataInvalidateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteSubtask(String token, SubtaskDto subtaskDto) throws ResponseException {
        Subtask subtask = subtaskDto.toSubtask();
        try {
            if (!subtaskRepository.isSubtaskBelongToUser(authorizeUser(token), subtask.getTaskId(), subtask.getId())) {
                throw new ResponseException(ResponseValue.ACCESS_DENIED);
            }
            subtaskRepository.deleteSubtask(subtask);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        } catch (DataInvalidateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SubtaskDto getSubtask(String token, int taskId, int id) throws ResponseException {
        try {
            if (!subtaskRepository.isSubtaskBelongToUser(authorizeUser(token), taskId, id)) {
                throw new ResponseException(ResponseValue.ACCESS_DENIED);
            }
            Subtask subtask = subtaskRepository.getSubtask(id);
            if (subtask == null) {
                throw new ResponseException(ResponseValue.ITEM_NOT_FOUND);
            }
            return SubtaskDto.fromSubtask(subtask);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public List<SubtaskDto> getAllSubtaskOfTask(String token, int taskId) throws ResponseException {
        try {
            if (!taskRepository.isTaskBelongToUser(authorizeUser(token), taskId)) {
                throw new ResponseException(ResponseValue.ACCESS_DENIED);
            }
            List<Subtask> subtasks = subtaskRepository.getAllSubtaskOfTask(taskId);
            return mapDtoList(subtasks);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
