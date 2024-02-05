package me.lordierclaw.todoserver.controller.api;

import com.google.gson.Gson;
import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.exception.response.ResponseValue;
import me.lordierclaw.todoserver.model.dto.CategoryDto;
import me.lordierclaw.todoserver.service.CategoryService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/api/category/*"})
public class CategoryAPI extends BaseCrudAPI {
    @Inject
    private CategoryService categoryService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        defaultSetup(req, resp);
        try {
            String authorization = getAuthorization(req);
            Gson gson = new Gson();
            List<CategoryDto> contents = handleGetContent(authorization, req);
            String result = gson.toJson(contents);
            resp.setStatus(ResponseValue.SUCCESS.getHttpStatus());
            resp.getOutputStream().print(result);
        } catch (NumberFormatException e) {
            throw new ResponseException(ResponseValue.FIELD_VALIDATION_ERROR);
        }
    }

    private List<CategoryDto> handleGetContent(String authorization, HttpServletRequest req) throws ResponseException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            if (!req.getParameterNames().hasMoreElements()) {
                return categoryService.getAllCategoryOfUser(authorization);
            }
            throw new ResponseException(ResponseValue.INVALID_OR_MISSING_REQUEST_PARAMS);
        } else {
            String[] paths = pathInfo.split("/");
            if (paths.length != 2) {
                throw new ResponseException(ResponseValue.INVALID_OR_MISSING_REQUEST_PARAMS);
            }
            int categoryId = Integer.parseInt(paths[1]);
            List<CategoryDto> singleCategory = new ArrayList<>();
            CategoryDto categoryDto = categoryService.getCategory(authorization, categoryId);
            singleCategory.add(categoryDto);
            return singleCategory;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        defaultSetup(req, resp);
        String authorization = getAuthorization(req);
        Gson gson = new Gson();
        CategoryDto categoryDto = gson.fromJson(req.getReader(), CategoryDto.class);
        categoryService.insertCategory(authorization, categoryDto);
        resp.setStatus(ResponseValue.SUCCESS.getHttpStatus());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        defaultSetup(req, resp);
        try {
            String authorization = getAuthorization(req);
            int categoryId = getIdFromPath(req.getPathInfo());
            Gson gson = new Gson();
            CategoryDto categoryDto = gson.fromJson(req.getReader(), CategoryDto.class);
            categoryDto.setId(categoryId);
            categoryService.updateCategory(authorization, categoryDto);
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
            int categoryId = getIdFromPath(req.getPathInfo());
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(categoryId);
            categoryService.deleteCategory(authorization, categoryDto);
            resp.setStatus(ResponseValue.SUCCESS.getHttpStatus());
        } catch (NumberFormatException e) {
            throw new ResponseException(ResponseValue.FIELD_VALIDATION_ERROR);
        }
    }
}
