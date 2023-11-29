package me.lordierclaw.todoserver.model.dto;

import me.lordierclaw.todoserver.model.base.Category;
import me.lordierclaw.todoserver.model.base.Identifiable;

public class CategoryDto extends Identifiable {
    private String name;

    public static CategoryDto fromCategory(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public Category toCategory(int userId) {
        Category category = new Category();
        category.setId(this.id);
        category.setName(this.name);
        category.setUserId(userId);
        return category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
