package com.retro.retrobe.util;

import com.retro.retrobe.model.UserBoard;
import com.retro.retrobe.payload.*;

public class ModalMap {
    public static Board mapToUserBoardToBoard(UserBoard userBoard) {
        Board board = new Board();
        board.setId(userBoard.getId());
        board.setName(userBoard.getName());
        board.setDescription(userBoard.getDescription());
        if (userBoard.getCurrentUser() != null) {
            LoggedUser loggedUser = new LoggedUser();
            loggedUser.setId(userBoard.getCurrentUser().getId());
            loggedUser.setCanContribute(userBoard.getCurrentUser().isCanContribute());
            loggedUser.setEndCommentNotification(userBoard.getCurrentUser().isEndCommentNotification());
            loggedUser.setSendCardNotification(userBoard.getCurrentUser().isSendCardNotification());
            board.setCurrentUser(loggedUser);
        }

        if (userBoard.getOwner() != null) {
            Owner owner = new Owner();
            owner.setEmail(userBoard.getOwner().getEmail());
            owner.setFullName(userBoard.getOwner().getFullName());
            board.setOwner(owner);
        }

        if (userBoard.getBoardMembers() != null && userBoard.getBoardMembers().size() > 0) {
            userBoard.getBoardMembers().forEach(boardMember -> {
                Member member = new Member();
                member.setName(boardMember.getName());
                member.setAbbreviation(boardMember.getAbbreviation());
                member.setActive(boardMember.isActive());
                member.setBoardOwner(boardMember.isBoardOwner());
                member.setEmailAddress(boardMember.getEmailAddress());
                member.setCanContribute(boardMember.isCanContribute());
                member.setFirstName(boardMember.getFirstName());
                member.setLastName(boardMember.getLastName());
                member.setFullName(boardMember.getFullName());
                board.addBoardMember(member);
            });
        }

        if (userBoard.getCardCategories() != null && userBoard.getCardCategories().size() > 0) {
            userBoard.getCardCategories().forEach(userCard -> {
                Category category = new Category();
                category.setName(userCard.getName());
                category.setDescription(userCard.getDescription());
                category.setColor(userCard.getColor());
                board.addCardCategory(category);
            });
        }

        board.setArchived(userBoard.isArchived());
        board.setTitle(userBoard.getTitle());
        return board;
    }
}
