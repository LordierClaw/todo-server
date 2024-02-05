package me.lordierclaw.todoserver.controller.api;

import com.google.gson.Gson;
import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.exception.response.ResponseValue;
import me.lordierclaw.todoserver.model.dto.SubtaskDto;
import me.lordierclaw.todoserver.service.SubtaskService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/api/subtask/*"})
public class SubtaskAPI extends BaseCrudAPI {
    @Inject
    private SubtaskService subtaskService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        defaultSetup(req, resp);
        try {
            String authorization = getAuthorization(req);
            Gson gson = new Gson();
            List<SubtaskDto> contents = handleGetContent(authorization, req);
            String result = gson.toJson(contents);
            resp.setStatus(ResponseValue.SUCCESS.getHttpStatus());
            resp.getOutputStream().print(result);
        } catch (NumberFormatException e) {
            throw new ResponseException(ResponseValue.FIELD_VALIDATION_ERROR);
        }
    }

    private List<SubtaskDto> handleGetContent(String authorization, HttpServletRequest req) throws ResponseException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            String taskId = req.getParameter("task");
            if (taskId == null) {
                throw new ResponseException(ResponseValue.INVALID_OR_MISSING_REQUEST_PARAMS);
            }
            return subtaskService.getAllSubtaskOfTask(authorization, Integer.parseInt(taskId));

        } else {
            String[] paths = pathInfo.split("/");
            if (paths.length != 2) {
                throw new ResponseException(ResponseValue.INVALID_OR_MISSING_REQUEST_PARAMS);
            }
            int subtaskId = Integer.parseInt(paths[1]);
            String taskId = req.getParameter("task");
            if (taskId == null) {
                throw new ResponseException(ResponseValue.INVALID_OR_MISSING_REQUEST_PARAMS);
            }
            List<SubtaskDto> singleSubtask = new ArrayList<>();
            SubtaskDto subtaskDto = subtaskService.getSubtask(authorization, Integer.parseInt(taskId), subtaskId);
            singleSubtask.add(subtaskDto);
            return singleSubtask;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        defaultSetup(req, resp);
        String authorization = getAuthorization(req);
        Gson gson = new Gson();
        SubtaskDto subtaskDto = gson.fromJson(req.getReader(), SubtaskDto.class);
        subtaskService.insertSubtask(authorization, subtaskDto);
        resp.setStatus(ResponseValue.SUCCESS.getHttpStatus());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        defaultSetup(req, resp);
        try {
            String authorization = getAuthorization(req);
            int subtaskId = getIdFromPath(req.getPathInfo());
            Gson gson = new Gson();
            SubtaskDto subtaskDto = gson.fromJson(req.getReader(), SubtaskDto.class);
            subtaskDto.setId(subtaskId);
            subtaskService.updateSubtask(authorization, subtaskDto);
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
            int subtaskId = getIdFromPath(req.getPathInfo());
            String taskId = req.getParameter("task");
            if (taskId == null) {
                throw new ResponseException(ResponseValue.INVALID_OR_MISSING_REQUEST_PARAMS);
            }
            SubtaskDto subtaskDto = new SubtaskDto();
            subtaskDto.setId(subtaskId);
            subtaskDto.setTaskId(Integer.parseInt(taskId));
            subtaskService.deleteSubtask(authorization, subtaskDto);
            resp.setStatus(ResponseValue.SUCCESS.getHttpStatus());
        } catch (NumberFormatException e) {
            throw new ResponseException(ResponseValue.FIELD_VALIDATION_ERROR);
        }
    }
}
