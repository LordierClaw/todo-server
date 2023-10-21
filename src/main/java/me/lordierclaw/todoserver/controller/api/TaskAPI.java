package me.lordierclaw.todoserver.controller.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lordierclaw.todoserver.model.client.TaskClient;
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

@WebServlet(urlPatterns = {"/api/task"})
public class TaskAPI extends HttpServlet {
    @Inject
    private ITaskService taskService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            String authorization = Helper.requireNotNull(req.getHeader("Authorization"));
            Gson gson = new Gson();
            JsonObject request = gson.fromJson(req.getReader(), JsonObject.class);
            List<?> contents = Helper.requireNotNull(handleGetContent(authorization, request));
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
    private List<?> handleGetContent(String authorization, JsonObject request) throws UnauthorizedException, NullPointerException {
        String type = request.get("type").getAsString();
        JsonObject param = request.get("params").getAsJsonObject();
        switch (type) {
            case "single":
                int taskId = param.get("id").getAsInt();
                List<TaskClient> singleTask = new ArrayList<>();
                TaskClient taskClient = taskService.getTask(authorization, taskId);
                singleTask.add(taskClient);
                return singleTask;
            case "all":
                return taskService.getAllTaskOfUser(authorization);
            case "in-category":
                int categoryId = param.get("category_id").getAsInt();
                return taskService.getAllTaskInCategory(authorization, categoryId);
            case "contains-title":
                String keyword = param.get("keyword").getAsString();
                return taskService.getAllTaskOfUserContainsTitle(authorization, keyword);
            case "in-range-time":
                long startTime = param.get("start_time").getAsLong();
                long endTime = param.get("end_time").getAsLong();
                return taskService.getAllTaskOfUserInRangeTime(authorization, startTime, endTime);
            case "task-count-by-status":
                boolean status = param.get("status").getAsBoolean();
                return taskService.getTaskCountByStatusOfUser(authorization, status);
            case "category-count":
                return taskService.getCategoryCountsOfUser(authorization);
            default:
                return null;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            String authorization = Helper.requireNotNull(req.getHeader("Authorization"));
            Gson gson = new Gson();
            TaskClient taskClient = gson.fromJson(req.getReader(), TaskClient.class);
            taskService.insertTask(authorization, taskClient);
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
            Gson gson = new Gson();
            TaskClient taskClient = gson.fromJson(req.getReader(), TaskClient.class);
            taskService.updateTask(authorization, taskClient);
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
            Gson gson = new Gson();
            TaskClient taskClient = gson.fromJson(req.getReader(), TaskClient.class);
            taskService.deleteTask(authorization, taskClient);
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
