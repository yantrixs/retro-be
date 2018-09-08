package com.retro.retrobe.payload;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class TemplateRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String imageName;

    private List<Category> categories = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
