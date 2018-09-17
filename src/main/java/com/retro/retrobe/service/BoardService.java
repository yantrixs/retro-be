package com.retro.retrobe.service;

import com.retro.retrobe.model.*;
import com.retro.retrobe.payload.BoardResponse;
import com.retro.retrobe.payload.UserBoardRequest;
import com.retro.retrobe.repository.BoardRepository;
import com.retro.retrobe.security.UserPrincipal;
import com.retro.retrobe.util.AppUtil;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private BoardRepository repository;

    public BoardService(final BoardRepository repository) {
        this.repository = repository;
    }

    public BoardResponse saveBoardInfo(UserBoardRequest request, UserPrincipal currentUser) {
        UserBoard userBoard = new UserBoard();
        userBoard.setTitle(request.getTitle());
        userBoard.setName(AppUtil.randomAlphaNumeric(22));
        if (request.getCategories() != null && request.getCategories().size() > 0) {
            request.getCategories().forEach(category -> {
                UserCard userCard = new UserCard();
                userCard.setName(category.getName());
                userCard.setDescription(category.getDescription());
                userCard.setColor(category.getColor());
                userBoard.addCardCategory(userCard);
            });
        }
        Owner owner = new Owner();
        String firstName = currentUser.getName();
        String lastName = (currentUser.getUsername() != null) ? currentUser.getUsername() : "";
        owner.setFullName(firstName + " " + lastName);
        owner.setEmail(currentUser.getEmail());
        userBoard.setOwner(owner);

        CurrentUser currentUserInfo = new CurrentUser();
        currentUserInfo.setCanContribute(true);
        currentUserInfo.setEndCommentNotification(false);
        currentUserInfo.setSendCardNotification(true);
        userBoard.setCurrentUser(currentUserInfo);

        // BoardMember Info
        BoardMember boardMember = new BoardMember();
        boardMember.setAbbreviation(firstName.substring(0, 1) + " " + lastName.substring(0, 1));
        boardMember.setEmailAddress(currentUser.getEmail());
        boardMember.setActive(currentUser.isEnabled());
        boardMember.setFirstName(currentUser.getName());
        boardMember.setCanContribute(currentUser.isEnabled());
        boardMember.setLastName(currentUser.getUsername());
        boardMember.setFullName(firstName + " " + lastName);
        // need to set logic
        boardMember.setBoardOwner(true);
        userBoard.addBoardMember(boardMember);

        UserBoard savedUserBoard = repository.save(userBoard);
        BoardResponse response = new BoardResponse();
        response.setId(savedUserBoard.getId());
        response.setDescription("");
        response.setName(savedUserBoard.getName());
        return response;
    }
}
