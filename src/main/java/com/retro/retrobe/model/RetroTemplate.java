package com.retro.retrobe.model;

import com.retro.retrobe.model.audit.DateAudit;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "templates")
public class RetroTemplate extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 200)
    private String description;

    @NotBlank
    @Size(max = 40)
    private String imageName;

    @OneToMany(
            mappedBy = "template",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CardCategory> cardCategories = new ArrayList<>();

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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<CardCategory> getCardCategories() {
        return cardCategories;
    }

    public void setCardCategories(List<CardCategory> cardCategories) {
        this.cardCategories = cardCategories;
    }

    public void addCardCategory(CardCategory cardCategory) {
        cardCategories.add(cardCategory);
        cardCategory.setTemplate(this);
    }

    public void removeCardCategory(CardCategory cardCategory) {
        cardCategories.remove(cardCategory);
        cardCategory.setTemplate(null);
    }
}
