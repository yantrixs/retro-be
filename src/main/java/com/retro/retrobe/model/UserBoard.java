package com.retro.retrobe.model;

import com.retro.retrobe.model.audit.DateAudit;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_boards", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})
public class UserBoard extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @NotBlank
    @Size(min = 22)
    @Size(max = 22)
    private String name;

    @NotBlank
    @Size(max = 40)
    private String title;

    @Size(max = 40)
    private String description;

    @OneToOne(mappedBy = "userBoardCurrentUser", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private CurrentUser currentUser;

    @OneToMany(
            mappedBy = "userMember",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<BoardMember> boardMembers = new ArrayList<>();

    @OneToOne(mappedBy = "userOwner", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Owner owner;

    @OneToMany(
            mappedBy = "userRetroCard",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<UserCard> cardCategories = new ArrayList<>();

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

    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CurrentUser currentUser) {
        if (currentUser == null) {
            if (this.currentUser != null) {
                this.currentUser.setUserBoardCurrentUser(null);
            }
        } else {
            currentUser.setUserBoardCurrentUser(this);
        }
        this.currentUser = currentUser;
    }


    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        if (owner == null) {
            if (this.owner != null) {
                this.owner.setUserOwner(null);
            }
        } else {
            owner.setUserOwner(this);
        }
        this.owner = owner;
    }

    public List<BoardMember> getBoardMembers() {
        return boardMembers;
    }

    public void setBoardMembers(List<BoardMember> boardMembers) {
        this.boardMembers = boardMembers;
    }

    public List<UserCard> getCardCategories() {
        return cardCategories;
    }

    public void setCardCategories(List<UserCard> cardCategories) {
        this.cardCategories = cardCategories;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public void addBoardMember(BoardMember member) {
        boardMembers.add(member);
        member.setUserMember(this);
    }

    public void removeBoardMember(BoardMember member) {
        boardMembers.remove(member);
        member.setUserMember(null);
    }

    public void addCardCategory(UserCard userCard) {
        cardCategories.add(userCard);
        userCard.setUserRetroCard(this);
    }

    public void removeCardCategory(UserCard userCard) {
        cardCategories.remove(userCard);
        userCard.setUserRetroCard(null);
    }
}
