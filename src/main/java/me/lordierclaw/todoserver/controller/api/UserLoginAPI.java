package me.lordierclaw.todoserver.controller.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.exception.response.ResponseValue;
import me.lordierclaw.todoserver.service.UserService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/api/login"})
public class UserLoginAPI extends HttpServlet {

    @Inject
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject body = gson.fromJson(req.getReader(), JsonObject.class);
        String email = body.get("email").getAsString();
        String password = body.get("password").getAsString();
        if (email == null || password == null) {
            throw new ResponseException(ResponseValue.INVALID_FIELDS);
        }
        String token = userService.logIn(email, password);
        resp.setStatus(ResponseValue.SUCCESS.getHttpStatus());
        resp.getOutputStream().print(token);
    }
}
