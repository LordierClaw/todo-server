package me.lordierclaw.todoserver.controller.api;

import com.google.gson.Gson;
import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.exception.response.ResponseValue;
import me.lordierclaw.todoserver.model.dto.TaskDto;
import me.lordierclaw.todoserver.service.ITaskService;
import me.lordierclaw.todoserver.utils.Helper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/api/task/*"})
public class TaskAPI extends HttpServlet {
    @Inject
    private ITaskService taskService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            String authorization = Helper.requireNotNull(req.getHeader("Authorization"), ResponseValue.MISSING_CLIENT_ID_OR_SECRET);
            Gson gson = new Gson();
            List<?> contents = handleGetContent(authorization, req);
            String result = gson.toJson(contents);
            resp.setStatus(ResponseValue.SUCCESS.getHttpStatus());
            resp.getOutputStream().print(result);
        } catch (NumberFormatException e) {
            throw new ResponseException(ResponseValue.FIELD_VALIDATION_ERROR);
        }
    }

    private List<?> handleGetContent(String authorization, HttpServletRequest req) throws ResponseException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            if (!req.getParameterNames().hasMoreElements()) {
                return taskService.getAllTaskOfUser(authorization);
            }

            String categoryId = req.getParameter("category");
            if (categoryId != null) {
                return taskService.getAllTaskInCategory(authorization, Integer.parseInt(categoryId));
            }

            String contains = req.getParameter("contains");
            if (contains != null) {
                return taskService.getAllTaskOfUserContainsTitle(authorization, contains);
            }

            String startTime = req.getParameter("start");
            String endTime = req.getParameter("end");
            if (startTime != null && endTime != null) {
                return taskService.getAllTaskOfUserInRangeTime(authorization, Long.parseLong(startTime), Long.parseLong(endTime));
            }

            String countType = req.getParameter("count");
            if (countType.equals("status")) {
                String countStatus = req.getParameter("value");
                return taskService.getTaskCountByStatusOfUser(authorization, Boolean.parseBoolean(countStatus));
            } else if (countType.equals("category")) {
                return taskService.getCategoryCountsOfUser(authorization);
            }
            throw new ResponseException(ResponseValue.INVALID_OR_MISSING_REQUEST_PARAMS);
        }

        String[] paths = pathInfo.split("/");
        if (paths.length != 2) {
            throw new ResponseException(ResponseValue.INVALID_OR_MISSING_REQUEST_PARAMS);
        }
        int taskId = Integer.parseInt(paths[1]);
        List<TaskDto> singleTask = new ArrayList<>();
        TaskDto taskDto = taskService.getTask(authorization, taskId);
        singleTask.add(taskDto);
        return singleTask;
    }

    private Integer getIdFromPath(String pathInfo) {
        if (pathInfo == null || pathInfo.equals("/")) {
            return null;
        }
        String[] splits = pathInfo.split("/");
        if (splits.length != 2) {
            return null;
        }
        return Integer.parseInt(splits[1]);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String authorization = Helper.requireNotNull(req.getHeader("Authorization"), ResponseValue.MISSING_CLIENT_ID_OR_SECRET);
        Gson gson = new Gson();
        TaskDto taskDto = gson.fromJson(req.getReader(), TaskDto.class);
        taskService.insertTask(authorization, taskDto);
        resp.setStatus(ResponseValue.SUCCESS.getHttpStatus());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            String authorization = Helper.requireNotNull(req.getHeader("Authorization"), ResponseValue.MISSING_CLIENT_ID_OR_SECRET);
            int taskId = getIdFromPath(req.getPathInfo());
            Gson gson = new Gson();
            TaskDto taskDto = gson.fromJson(req.getReader(), TaskDto.class);
            taskDto.setId(taskId);
            taskService.updateTask(authorization, taskDto);
            resp.setStatus(ResponseValue.SUCCESS.getHttpStatus());
        } catch (NumberFormatException e) {
            throw new ResponseException(ResponseValue.FIELD_VALIDATION_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            String authorization = Helper.requireNotNull(req.getHeader("Authorization"), ResponseValue.MISSING_CLIENT_ID_OR_SECRET);
            int taskId = getIdFromPath(req.getPathInfo());
            TaskDto taskDto = new TaskDto();
            taskDto.setId(taskId);
            taskService.deleteTask(authorization, taskDto);
            resp.setStatus(ResponseValue.SUCCESS.getHttpStatus());
        } catch (NumberFormatException e) {
            throw new ResponseException(ResponseValue.FIELD_VALIDATION_ERROR);
        }
    }
}
