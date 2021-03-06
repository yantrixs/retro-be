package com.retro.retrobe.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "card_categories")
public class CardCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 200)
    private String description;

    @NotBlank
    @Size(max = 40)
    private String color;

    @Id
    @ManyToOne(targetEntity = RetroTemplate.class)
    @PrimaryKeyJoinColumn(name = "template_id", referencedColumnName = "id")
    private RetroTemplate template;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public RetroTemplate getTemplate() {
        return template;
    }

    void setTemplate(RetroTemplate template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardCategory cardCategory = (CardCategory) o;
        return Objects.equals(id, cardCategory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
