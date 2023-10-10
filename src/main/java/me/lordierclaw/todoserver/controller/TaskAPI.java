package me.lordierclaw.todoserver.controller;

import com.google.gson.Gson;
import me.lordierclaw.todoserver.model.base.Task;
import me.lordierclaw.todoserver.model.client.TaskClient;
import me.lordierclaw.todoserver.service.ITaskService;
import me.lordierclaw.todoserver.service.exception.UnauthorizedException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/api/task"})
public class TaskAPI extends HttpServlet {
    @Inject
    private ITaskService taskService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String authorization = req.getHeader("Authorization");
        try {
            List<TaskClient> tasks = taskService.getAllTaskOfUser(authorization);
            Gson gson = new Gson();
            String result = gson.toJson(tasks);
            resp.setStatus(200);
            resp.getOutputStream().print(result);
        } catch (UnauthorizedException e) {
            resp.setStatus(401);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
