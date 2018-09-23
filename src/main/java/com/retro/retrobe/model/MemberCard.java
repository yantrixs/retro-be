package com.retro.retrobe.model;

import com.retro.retrobe.model.audit.DateAudit;

import javax.persistence.*;

@Entity
@Table(name = "cards")
public class MemberCard extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String boardName;
    private String boardTitle;
    private String cardCategoryName;
    private String memberEmail;
    private String message;
    private int likeCount;
    private String likeMessage;
    private int dislikeCount;
    private String dislikeMessage;
    private String memberName;
    private String memberAbbreviation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getCardCategoryName() {
        return cardCategoryName;
    }

    public void setCardCategoryName(String cardCategoryName) {
        this.cardCategoryName = cardCategoryName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getLikeMessage() {
        return likeMessage;
    }

    public void setLikeMessage(String likeMessage) {
        this.likeMessage = likeMessage;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public String getDislikeMessage() {
        return dislikeMessage;
    }

    public void setDislikeMessage(String dislikeMessage) {
        this.dislikeMessage = dislikeMessage;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberAbbreviation() {
        return memberAbbreviation;
    }

    public void setMemberAbbreviation(String memberAbbreviation) {
        this.memberAbbreviation = memberAbbreviation;
    }
}
