package me.lordierclaw.todoserver.model.dto;

import me.lordierclaw.todoserver.model.base.Attachment;
import me.lordierclaw.todoserver.model.base.Identifiable;

public class AttachmentDto extends Identifiable {
    private String type;
    private String url;
    private int taskId;

    public static AttachmentDto fromAttachment(Attachment attachment) {
        AttachmentDto attachmentDto = new AttachmentDto();
        attachmentDto.setId(attachment.getId());
        attachmentDto.setType(attachmentDto.getType());
        attachmentDto.setUrl(attachmentDto.getUrl());
        attachmentDto.setTaskId(attachmentDto.getTaskId());
        return attachmentDto;
    }

    public Attachment toAttachment() {
        Attachment attachment = new Attachment();
        attachment.setId(id);
        attachment.setType(type);
        attachment.setUrl(url);
        attachment.setTaskId(taskId);
        return attachment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
