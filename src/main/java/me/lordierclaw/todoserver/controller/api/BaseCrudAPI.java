package me.lordierclaw.todoserver.controller.api;

import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.exception.response.ResponseValue;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseCrudAPI extends HttpServlet {
    protected static String DEFAULT_ENCODING = "utf-8";

    protected void defaultSetup(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(DEFAULT_ENCODING);
        resp.setCharacterEncoding(DEFAULT_ENCODING);
    }

    protected String getAuthorization(HttpServletRequest req) throws ResponseException {
        String auth = req.getHeader("Authorization");
        if (auth == null) {
            throw new ResponseException(ResponseValue.MISSING_CLIENT_ID_OR_SECRET);
        }
        return auth;
    }

    protected Integer getIdFromPath(String pathInfo) {
        if (pathInfo == null || pathInfo.equals("/")) {
            return null;
        }
        String[] splits = pathInfo.split("/");
        if (splits.length != 2) {
            return null;
        }
        return Integer.parseInt(splits[1]);
    }
}
