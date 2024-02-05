package me.lordierclaw.todoserver.exception.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ResponseExceptionHandler")
public class ResponseExceptionHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        processError(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        processError(req, resp);
    }

    private void processError(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseException exception = (ResponseException) req.getAttribute("javax.servlet.error.exception");
        PrintWriter out = resp.getWriter();
        out.write(exception.getMessage());
    }
}
