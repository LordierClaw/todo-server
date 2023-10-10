package me.lordierclaw.todoserver.model.base;

public class Identifiable {
    protected Integer id;

    public void setId(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }
}
