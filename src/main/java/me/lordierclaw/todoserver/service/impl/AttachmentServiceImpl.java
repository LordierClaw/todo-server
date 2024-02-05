package me.lordierclaw.todoserver.service.impl;

import me.lordierclaw.todoserver.exception.data.DataCrudException;
import me.lordierclaw.todoserver.exception.data.DataInvalidateException;
import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.exception.response.ResponseValue;
import me.lordierclaw.todoserver.model.base.Attachment;
import me.lordierclaw.todoserver.model.dto.AttachmentDto;
import me.lordierclaw.todoserver.repository.AttachmentRepository;
import me.lordierclaw.todoserver.repository.TaskRepository;
import me.lordierclaw.todoserver.service.AttachmentService;
import me.lordierclaw.todoserver.service.AuthorizedService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class AttachmentServiceImpl extends AuthorizedService implements AttachmentService {
    @Inject
    private AttachmentRepository attachmentRepository;
    @Inject
    private TaskRepository taskRepository;

    private List<AttachmentDto> mapDtoList(List<Attachment> attachments) throws ResponseException {
        if (attachments == null) throw new ResponseException(ResponseValue.ITEM_NOT_FOUND);
        List<AttachmentDto> results = new ArrayList<>();
        for (Attachment attachment : attachments) {
            results.add(AttachmentDto.fromAttachment(attachment));
        }
        return results;
    }

    @Override
    public int insertAttachment(String token, AttachmentDto attachmentDto) throws ResponseException {
        Attachment attachment = attachmentDto.toAttachment();
        try {
            if (!taskRepository.isTaskBelongToUser(authorizeUser(token), attachment.getTaskId())) {
                throw new ResponseException(ResponseValue.ACCESS_DENIED);
            }
            return attachmentRepository.insertAttachment(attachment);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        } catch (DataInvalidateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAttachment(String token, AttachmentDto attachmentDto) throws ResponseException {
        Attachment attachment = attachmentDto.toAttachment();
        try {
            if (!attachmentRepository.isAttachmentBelongToUser(authorizeUser(token), attachment.getTaskId(), attachment.getId())) {
                throw new ResponseException(ResponseValue.ACCESS_DENIED);
            }
            attachmentRepository.updateAttachment(attachment);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        } catch (DataInvalidateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAttachment(String token, AttachmentDto attachmentDto) throws ResponseException {
        Attachment attachment = attachmentDto.toAttachment();
        try {
            if (!attachmentRepository.isAttachmentBelongToUser(authorizeUser(token), attachment.getTaskId(), attachment.getId())) {
                throw new ResponseException(ResponseValue.ACCESS_DENIED);
            }
            attachmentRepository.deleteAttachment(attachment);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        } catch (DataInvalidateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AttachmentDto getAttachment(String token, int taskId, int id) throws ResponseException {
        try {
            if (!attachmentRepository.isAttachmentBelongToUser(authorizeUser(token), taskId, id)) {
                throw new ResponseException(ResponseValue.ACCESS_DENIED);
            }
            Attachment attachment = attachmentRepository.getAttachment(id);
            if (attachment == null) {
                throw new ResponseException(ResponseValue.ITEM_NOT_FOUND);
            }
            return AttachmentDto.fromAttachment(attachment);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public List<AttachmentDto> getAllAttachmentOfTask(String token, int taskId) throws ResponseException {
        try {
            if (!taskRepository.isTaskBelongToUser(authorizeUser(token), taskId)) {
                throw new ResponseException(ResponseValue.ACCESS_DENIED);
            }
            List<Attachment> list = attachmentRepository.getAllAttachmentsOfTask(taskId);
            return mapDtoList(list);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
