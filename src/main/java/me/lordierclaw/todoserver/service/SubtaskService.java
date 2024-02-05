package me.lordierclaw.todoserver.service;

import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.model.dto.SubtaskDto;

import java.util.List;

public interface SubtaskService {
    int insertSubtask(String token, SubtaskDto subtaskDto) throws ResponseException;

    void updateSubtask(String token, SubtaskDto subtaskDto) throws ResponseException;

    void deleteSubtask(String token, SubtaskDto subtaskDto) throws ResponseException;

    SubtaskDto getSubtask(String token, int taskId, int id) throws ResponseException;

    List<SubtaskDto> getAllSubtaskOfTask(String token, int taskId) throws ResponseException;
}
