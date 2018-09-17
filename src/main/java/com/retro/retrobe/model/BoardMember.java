package com.retro.retrobe.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "board_members")
public class BoardMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String abbreviation;
    private String firstName;
    private String lastName;
    private String fullName;
    private String emailAddress;
    private boolean isBoardOwner;
    private boolean isActive;
    private boolean canContribute;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserBoard.class)
    @JoinColumn(name = "user_board_member_id", nullable = false)
    private UserBoard userMember;

    @OneToMany(
            mappedBy = "boardMemberPermission",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<BoardMemberPermission> boardMemberPermissions = new ArrayList<>();

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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isBoardOwner() {
        return isBoardOwner;
    }

    public void setBoardOwner(boolean boardOwner) {
        isBoardOwner = boardOwner;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isCanContribute() {
        return canContribute;
    }

    public void setCanContribute(boolean canContribute) {
        this.canContribute = canContribute;
    }

    public List<BoardMemberPermission> getBoardMemberPermissions() {
        return boardMemberPermissions;
    }

    public void setBoardMemberPermissions(List<BoardMemberPermission> boardMemberPermissions) {
        this.boardMemberPermissions = boardMemberPermissions;
    }

    public UserBoard getUserMember() {
        return userMember;
    }

    void setUserMember(UserBoard userMember) {
        this.userMember = userMember;
    }

    public void addBoardMemberPermission(BoardMemberPermission memberPermission) {
        boardMemberPermissions.add(memberPermission);
        memberPermission.setBoardMemberPermission(this);
    }

    public void removeBoardMemberPermission(BoardMemberPermission memberPermission) {
        boardMemberPermissions.remove(memberPermission);
        memberPermission.setBoardMemberPermission(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardMember boardMember = (BoardMember) o;
        return Objects.equals(id, boardMember.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
