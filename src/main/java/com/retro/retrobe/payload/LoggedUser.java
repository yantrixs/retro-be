package com.retro.retrobe.payload;

import java.util.ArrayList;
import java.util.List;

public class LoggedUser {
    private Long id;
    private boolean canContribute;
    private boolean sendCardNotification;
    private boolean endCommentNotification;

    private List<MemberPermission> boardMemberPermissions = new ArrayList<>();

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

    public List<MemberPermission> getBoardMemberPermissions() {
        return boardMemberPermissions;
    }

    public void setBoardMemberPermissions(List<MemberPermission> boardMemberPermissions) {
        this.boardMemberPermissions = boardMemberPermissions;
    }
}
