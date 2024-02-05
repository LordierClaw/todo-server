package me.lordierclaw.todoserver.service;

import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.model.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    int insertCategory(String token, CategoryDto categoryDto) throws ResponseException;

    void updateCategory(String token, CategoryDto categoryDto) throws ResponseException;

    void deleteCategory(String token, CategoryDto categoryDto) throws ResponseException;

    CategoryDto getCategory(String token, int id) throws ResponseException;

    List<CategoryDto> getAllCategoryOfUser(String token) throws ResponseException;
}
