package me.lordierclaw.todoserver.model.client;

import me.lordierclaw.todoserver.model.base.Category;
import me.lordierclaw.todoserver.model.base.Identifiable;

public class CategoryClient extends Identifiable {
    private String name;

    public static CategoryClient fromCategory(Category category) {
        CategoryClient categoryClient = new CategoryClient();
        categoryClient.setId(category.getId());
        categoryClient.setName(category.getName());
        return categoryClient;
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
