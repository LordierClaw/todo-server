package me.lordierclaw.todoserver.controller.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lordierclaw.todoserver.model.base.User;
import me.lordierclaw.todoserver.security.IPasswordEncoder;
import me.lordierclaw.todoserver.service.IUserService;
import me.lordierclaw.todoserver.utils.Status;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/register"})
public class UserRegisterAPI extends HttpServlet {
    @Inject
    private IUserService userService;
    @Inject
    private IPasswordEncoder passwordEncoder;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject body = gson.fromJson(req.getReader(), JsonObject.class);
        String name = body.get("name").getAsString();
        String email = body.get("email").getAsString();
        String password = body.get("password").getAsString();
        if (name == null || email == null || password == null) {
            resp.setStatus(Status.BAD_REQUEST);
            return;
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userService.insertUser(user);
        resp.setStatus(Status.ACCEPTED);
    }
}
