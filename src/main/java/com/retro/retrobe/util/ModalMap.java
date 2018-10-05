package com.retro.retrobe.util;

import com.retro.retrobe.model.BoardMember;
import com.retro.retrobe.model.MemberCard;
import com.retro.retrobe.model.UserBoard;
import com.retro.retrobe.payload.*;

import java.util.ArrayList;
import java.util.List;

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
            owner.setId(userBoard.getOwner().getId());
            owner.setEmail(userBoard.getOwner().getEmail());
            owner.setFullName(userBoard.getOwner().getFullName());
            board.setOwner(owner);
        }

        if (userBoard.getBoardMembers() != null && userBoard.getBoardMembers().size() > 0) {
            userBoard.getBoardMembers().forEach(boardMember -> {
                Member member = new Member();
                member.setId(boardMember.getId());
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

    public static MemberCard cardRequestToMemberCard(CardRequest request) {
        MemberCard memberCard = new MemberCard();
        memberCard.setBoardName(request.getBoardName());
        memberCard.setBoardTitle(request.getBoardTitle());
        memberCard.setCardCategoryName(request.getCardCategoryName());
        memberCard.setDislikeCount(request.getDislikeCount());
        memberCard.setDislikeMessage(request.getDislikeMessage());
        memberCard.setMemberEmail(request.getMemberEmail());
        memberCard.setMessage(request.getMessage());
        memberCard.setLikeMessage(request.getLikeMessage());
        memberCard.setLikeCount(request.getLikeCount());
        memberCard.setMemberName(request.getMemberName());
        memberCard.setMemberAbbreviation(request.getMemberAbbreviation());
        return memberCard;
    }

    public static CardResponse memberCardToCardResponse(MemberCard memberCard) {
        CardResponse cardResponse = new CardResponse();
        cardResponse.setId(memberCard.getId());
        cardResponse.setBoardTitle(memberCard.getBoardTitle());
        cardResponse.setBoardName(memberCard.getBoardName());
        cardResponse.setCardCategoryName(memberCard.getCardCategoryName());
        cardResponse.setCardDate(memberCard.getCreatedAt().toString());
        cardResponse.setDislikeCount(memberCard.getDislikeCount());
        cardResponse.setDislikeMessage(memberCard.getDislikeMessage());
        cardResponse.setMemberEmail(memberCard.getMemberEmail());
        cardResponse.setMessage(memberCard.getMessage());
        cardResponse.setLikeCount(memberCard.getLikeCount());
        cardResponse.setLikeMessage(memberCard.getLikeMessage());
        cardResponse.setMemberName(memberCard.getMemberName());
        cardResponse.setMemberAbbreviation(memberCard.getMemberAbbreviation());
        return cardResponse;
    }

    public static List<Member> boardMemberToMember(List<BoardMember> boardMembers) {
        List<Member> members = new ArrayList<>();
        boardMembers.forEach(boardMember -> {
            Member member = new Member();
            member.setId(boardMember.getId());
            member.setFullName(boardMember.getFullName());
            member.setName(boardMember.getName());
            member.setLastName(boardMember.getLastName());
            member.setFirstName(boardMember.getFirstName());
            member.setCanContribute(boardMember.isCanContribute());
            member.setActive(boardMember.isActive());
            member.setBoardOwner(boardMember.isBoardOwner());
            member.setEmailAddress(boardMember.getEmailAddress());
            member.setAbbreviation(boardMember.getAbbreviation());
            members.add(member);
        });

        return members;
    }
}
