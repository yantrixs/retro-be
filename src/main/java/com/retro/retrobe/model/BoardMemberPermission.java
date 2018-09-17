package com.retro.retrobe.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "BoardMemberPermission")
public class BoardMemberPermission implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CurrentUser.class)
    @JoinColumn(name = "current_user_id", nullable = false)
    private CurrentUser currentUser;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = BoardMember.class)
    @JoinColumn(name = "board_member_permission_id", nullable = false)
    private BoardMember boardMemberPermission;


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

    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    public BoardMember getBoardMemberPermission() {
        return boardMemberPermission;
    }

    public void setBoardMemberPermission(BoardMember boardMemberPermission) {
        this.boardMemberPermission = boardMemberPermission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardMemberPermission memberPermission = (BoardMemberPermission) o;
        return Objects.equals(id, memberPermission.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
