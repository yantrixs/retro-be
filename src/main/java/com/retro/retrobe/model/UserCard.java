package com.retro.retrobe.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_cards")
public class UserCard implements Serializable {
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

    @ManyToOne(targetEntity = UserBoard.class)
    @PrimaryKeyJoinColumn(name = "user_board_card_id", referencedColumnName = "id")
    private UserBoard userRetroCard;

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

    public UserBoard getUserRetroCard() {
        return userRetroCard;
    }

    public void setUserRetroCard(UserBoard userRetroCard) {
        this.userRetroCard = userRetroCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCard userCard = (UserCard) o;
        return Objects.equals(id, userCard.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
