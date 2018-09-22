package com.retro.retrobe.payload;

public class CardResponse {
    private String boarName;
    private String boardTitle;
    private String cardCategoryName;
    private String memberEmail;
    private String message;
    private int likeCount;
    private String likeMessage;
    private int dislikeCount;
    private String dislikeMessage;
    private String cardDate;

    public String getBoarName() {
        return boarName;
    }

    public void setBoarName(String boarName) {
        this.boarName = boarName;
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

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }
}
