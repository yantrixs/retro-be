package com.retro.retrobe.payload;

import java.util.ArrayList;
import java.util.List;

public class UserBoardRequest {
    private String title;
    private String description;
    private List<Category> categories = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
