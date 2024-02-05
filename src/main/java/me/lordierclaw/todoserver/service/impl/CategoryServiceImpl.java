package me.lordierclaw.todoserver.service.impl;

import me.lordierclaw.todoserver.exception.data.DataCrudException;
import me.lordierclaw.todoserver.exception.data.DataInvalidateException;
import me.lordierclaw.todoserver.exception.response.ResponseException;
import me.lordierclaw.todoserver.exception.response.ResponseValue;
import me.lordierclaw.todoserver.model.base.Category;
import me.lordierclaw.todoserver.model.dto.CategoryDto;
import me.lordierclaw.todoserver.repository.CategoryRepository;
import me.lordierclaw.todoserver.service.AuthorizedService;
import me.lordierclaw.todoserver.service.CategoryService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl extends AuthorizedService implements CategoryService {

    @Inject
    private CategoryRepository categoryRepository;

    private List<CategoryDto> mapDtoList(List<Category> categories) throws ResponseException {
        if (categories == null) {
            throw new ResponseException(ResponseValue.ITEM_NOT_FOUND);
        }
        ArrayList<CategoryDto> results = new ArrayList<>();
        for (Category category : categories) {
            results.add(CategoryDto.fromCategory(category));
        }
        return results;
    }

    @Override
    public int insertCategory(String token, CategoryDto categoryDto) throws ResponseException {
        Category category = categoryDto.toCategory(authorizeUser(token));
        try {
            return categoryRepository.insertCategory(category);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        } catch (DataInvalidateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCategory(String token, CategoryDto categoryDto) throws ResponseException {
        Category category = categoryDto.toCategory(authorizeUser(token));
        try {
            categoryRepository.updateCategory(category);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        } catch (DataInvalidateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCategory(String token, CategoryDto categoryDto) throws ResponseException {
        Category category = categoryDto.toCategory(authorizeUser(token));
        try {
            categoryRepository.deleteCategory(category);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        } catch (DataInvalidateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CategoryDto getCategory(String token, int id) throws ResponseException {
        try {
            Category category = categoryRepository.getCategory(authorizeUser(token), id);
            if (category == null) {
                throw new ResponseException(ResponseValue.ITEM_NOT_FOUND);
            }
            return CategoryDto.fromCategory(category);
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
    }

    @Override
    public List<CategoryDto> getAllCategoryOfUser(String token) throws ResponseException {
        try {
            return mapDtoList(categoryRepository.getAllCategoriesOfUser(authorizeUser(token)));
        } catch (DataCrudException e) {
            throw new ResponseException(ResponseValue.UNEXPECTED_ERROR_OCCURRED);
        }
    }
}
