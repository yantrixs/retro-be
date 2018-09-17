package com.retro.retrobe.payload;

public class Member {
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
}
