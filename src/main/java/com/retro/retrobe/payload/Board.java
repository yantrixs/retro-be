package com.retro.retrobe.payload;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Long id;
    private String name;
    private String title;
    private String description;
    private LoggedUser currentUser;
    private List<Member> boardMembers = new ArrayList<>();
    private Owner owner;
    private List<Category> cardCategories = new ArrayList<>();
    private boolean isArchived;

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

    public LoggedUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(LoggedUser currentUser) {
        this.currentUser = currentUser;
    }

    public List<Member> getBoardMembers() {
        return boardMembers;
    }

    public void setBoardMembers(List<Member> boardMembers) {
        this.boardMembers = boardMembers;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Category> getCardCategories() {
        return cardCategories;
    }

    public void setCardCategories(List<Category> cardCategories) {
        this.cardCategories = cardCategories;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public void addBoardMember(Member member) {
        boardMembers.add(member);
    }

    public void addCardCategory(Category category) {
        cardCategories.add(category);
    }
}
