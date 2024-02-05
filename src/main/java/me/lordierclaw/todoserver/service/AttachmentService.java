package me.lordierclaw.todoserver.service;

import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.model.dto.AttachmentDto;

import java.util.List;

public interface AttachmentService {
    int insertAttachment(String token, AttachmentDto attachmentDto) throws ResponseException;

    void updateAttachment(String token, AttachmentDto attachmentDto) throws ResponseException;

    void deleteAttachment(String token, AttachmentDto attachmentDto) throws ResponseException;

    AttachmentDto getAttachment(String token, int taskId, int id) throws ResponseException;

    List<AttachmentDto> getAllAttachmentOfTask(String token, int taskId) throws ResponseException;
}
