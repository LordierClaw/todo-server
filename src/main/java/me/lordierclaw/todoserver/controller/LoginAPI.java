package me.lordierclaw.todoserver.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lordierclaw.todoserver.service.IUserService;
import me.lordierclaw.todoserver.service.exception.LoginException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/api/login"})
public class LoginAPI extends HttpServlet {

    @Inject
    private IUserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject body = gson.fromJson(req.getReader(), JsonObject.class);
        String email = body.get("email").getAsString();
        String password = body.get("password").getAsString();
        if (email == null || password == null) {
            resp.setStatus(400); // Bad Request
            return;
        }
        try {
            String token = userService.logIn(email, password);
            resp.setStatus(202); // Accepted
            resp.getOutputStream().print(token);
        } catch (LoginException e) {
            resp.setStatus(406); // Not Accepted
        }
    }
}
