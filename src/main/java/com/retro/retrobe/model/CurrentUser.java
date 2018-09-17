package com.retro.retrobe.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="current_users")
public class CurrentUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean canContribute;
    private boolean sendCardNotification;
    private boolean endCommentNotification;

    @OneToMany(
            mappedBy = "currentUser",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<BoardMemberPermission> boardMemberPermissions = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_board_current_user_id")
    private UserBoard userBoardCurrentUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCanContribute() {
        return canContribute;
    }

    public void setCanContribute(boolean canContribute) {
        this.canContribute = canContribute;
    }

    public boolean isSendCardNotification() {
        return sendCardNotification;
    }

    public void setSendCardNotification(boolean sendCardNotification) {
        this.sendCardNotification = sendCardNotification;
    }

    public boolean isEndCommentNotification() {
        return endCommentNotification;
    }

    public void setEndCommentNotification(boolean endCommentNotification) {
        this.endCommentNotification = endCommentNotification;
    }

    public List<BoardMemberPermission> getBoardMemberPermissions() {
        return boardMemberPermissions;
    }

    public void setBoardMemberPermissions(List<BoardMemberPermission> boardMemberPermissions) {
        this.boardMemberPermissions = boardMemberPermissions;
    }

    public void addBoardPermission(BoardMemberPermission permissions) {
        boardMemberPermissions.add(permissions);
        permissions.setCurrentUser(this);
    }

    public void removeBoardPermission(BoardMemberPermission permissions) {
        boardMemberPermissions.remove(permissions);
        permissions.setCurrentUser(null);
    }

    public UserBoard getUserBoardCurrentUser() {
        return userBoardCurrentUser;
    }

    public void setUserBoardCurrentUser(UserBoard userBoardCurrentUser) {
        this.userBoardCurrentUser = userBoardCurrentUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentUser currentUser = (CurrentUser) o;
        return Objects.equals(id, currentUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
