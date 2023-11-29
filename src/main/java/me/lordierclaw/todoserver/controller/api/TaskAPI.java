package me.lordierclaw.todoserver.controller.api;

import com.google.gson.Gson;
import me.lordierclaw.todoserver.model.dto.TaskDto;
import me.lordierclaw.todoserver.service.ITaskService;
import me.lordierclaw.todoserver.service.exception.UnauthorizedException;
import me.lordierclaw.todoserver.utils.Helper;
import me.lordierclaw.todoserver.utils.Status;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/task/*"})
public class TaskAPI extends HttpServlet {
    @Inject
    private ITaskService taskService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            String authorization = Helper.requireNotNull(req.getHeader("Authorization"));
            Gson gson = new Gson();
            List<?> contents = Helper.requireNotNull(handleGetContent(authorization, req));
            String result = gson.toJson(contents);
            resp.setStatus(Status.OK);
            resp.getOutputStream().print(result);
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            resp.setStatus(Status.UNAUTHORIZED);
        } catch (NullPointerException e) {
            e.printStackTrace();
            resp.setStatus(Status.BAD_REQUEST);
        }
    }

    // This function might throw NullPointerException if it can't find the correct key, request
    private List<?> handleGetContent(String authorization, HttpServletRequest req) throws UnauthorizedException, NullPointerException {
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
            throw new NullPointerException(); // Bad request
        }

        String[] paths = pathInfo.split("/");
        if (paths.length != 2) {
            throw new NullPointerException(); // Bad request
        }
        int taskId = Integer.parseInt(paths[1]);
        List<TaskDto> singleTask = new ArrayList<>();
        TaskDto taskDto = taskService.getTask(authorization, taskId);
        singleTask.add(taskDto);
        return singleTask;
    }

    private Integer getIdFromPath(String pathInfo) {
        if(pathInfo == null || pathInfo.equals("/")){
            return null;
        }
        String[] splits = pathInfo.split("/");
        if(splits.length != 2) {
            return null;
        }
        try {
            return Integer.parseInt(splits[1]);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            String authorization = Helper.requireNotNull(req.getHeader("Authorization"));
            Gson gson = new Gson();
            TaskDto taskDto = gson.fromJson(req.getReader(), TaskDto.class);
            taskService.insertTask(authorization, taskDto);
            resp.setStatus(Status.OK);
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            resp.setStatus(Status.UNAUTHORIZED);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            String authorization = Helper.requireNotNull(req.getHeader("Authorization"));
            int taskId = getIdFromPath(req.getPathInfo());
            Gson gson = new Gson();
            TaskDto taskDto = gson.fromJson(req.getReader(), TaskDto.class);
            taskDto.setId(taskId);
            taskService.updateTask(authorization, taskDto);
            resp.setStatus(Status.OK);
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            resp.setStatus(Status.UNAUTHORIZED);
        } catch (NullPointerException e) {
            e.printStackTrace();
            resp.setStatus(Status.BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            String authorization = Helper.requireNotNull(req.getHeader("Authorization"));
            int taskId = getIdFromPath(req.getPathInfo());
            TaskDto taskDto = new TaskDto();
            taskDto.setId(taskId);
            taskService.deleteTask(authorization, taskDto);
            resp.setStatus(Status.OK);
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            resp.setStatus(Status.UNAUTHORIZED);
        } catch (NullPointerException e) {
            e.printStackTrace();
            resp.setStatus(Status.BAD_REQUEST);
        }
    }
}
