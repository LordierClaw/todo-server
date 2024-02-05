package me.lordierclaw.todoserver.controller.api;

import com.google.gson.Gson;
import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.exception.response.ResponseValue;
import me.lordierclaw.todoserver.model.dto.AttachmentDto;
import me.lordierclaw.todoserver.service.AttachmentService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/api/attachment/*"})
public class AttachmentAPI extends BaseCrudAPI {
    @Inject
    private AttachmentService attachmentService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        defaultSetup(req, resp);
        try {
            String authorization = getAuthorization(req);
            Gson gson = new Gson();
            List<AttachmentDto> contents = handleGetContent(authorization, req);
            String result = gson.toJson(contents);
            resp.setStatus(ResponseValue.SUCCESS.getHttpStatus());
            resp.getOutputStream().print(result);
        } catch (NumberFormatException e) {
            throw new ResponseException(ResponseValue.FIELD_VALIDATION_ERROR);
        }
    }

    private List<AttachmentDto> handleGetContent(String authorization, HttpServletRequest req) throws ResponseException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            String taskId = req.getParameter("task");
            if (taskId == null) {
                throw new ResponseException(ResponseValue.INVALID_OR_MISSING_REQUEST_PARAMS);
            }
            return attachmentService.getAllAttachmentOfTask(authorization, Integer.parseInt(taskId));

        } else {
            String[] paths = pathInfo.split("/");
            if (paths.length != 2) {
                throw new ResponseException(ResponseValue.INVALID_OR_MISSING_REQUEST_PARAMS);
            }
            int attachmentId = Integer.parseInt(paths[1]);
            String taskId = req.getParameter("task");
            if (taskId == null) {
                throw new ResponseException(ResponseValue.INVALID_OR_MISSING_REQUEST_PARAMS);
            }
            List<AttachmentDto> singleAttachment = new ArrayList<>();
            AttachmentDto attachmentDto = attachmentService.getAttachment(authorization, Integer.parseInt(taskId), attachmentId);
            singleAttachment.add(attachmentDto);
            return singleAttachment;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        defaultSetup(req, resp);
        String authorization = getAuthorization(req);
        Gson gson = new Gson();
        AttachmentDto attachmentDto = gson.fromJson(req.getReader(), AttachmentDto.class);
        attachmentService.insertAttachment(authorization, attachmentDto);
        resp.setStatus(ResponseValue.SUCCESS.getHttpStatus());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        defaultSetup(req, resp);
        try {
            String authorization = getAuthorization(req);
            int attachmentId = getIdFromPath(req.getPathInfo());
            Gson gson = new Gson();
            AttachmentDto attachmentDto = gson.fromJson(req.getReader(), AttachmentDto.class);
            attachmentDto.setId(attachmentId);
            attachmentService.updateAttachment(authorization, attachmentDto);
            resp.setStatus(ResponseValue.SUCCESS.getHttpStatus());
        } catch (NumberFormatException e) {
            throw new ResponseException(ResponseValue.FIELD_VALIDATION_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        defaultSetup(req, resp);
        try {
            String authorization = getAuthorization(req);
            int attachmentId = getIdFromPath(req.getPathInfo());
            String taskId = req.getParameter("task");
            if (taskId == null) {
                throw new ResponseException(ResponseValue.INVALID_OR_MISSING_REQUEST_PARAMS);
            }
            AttachmentDto attachmentDto = new AttachmentDto();
            attachmentDto.setId(attachmentId);
            attachmentDto.setTaskId(Integer.parseInt(taskId));
            attachmentService.deleteAttachment(authorization, attachmentDto);
            resp.setStatus(ResponseValue.SUCCESS.getHttpStatus());
        } catch (NumberFormatException e) {
            throw new ResponseException(ResponseValue.FIELD_VALIDATION_ERROR);
        }
    }
}
