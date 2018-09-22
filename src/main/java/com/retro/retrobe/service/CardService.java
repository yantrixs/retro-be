package com.retro.retrobe.service;

import com.retro.retrobe.model.MemberCard;
import com.retro.retrobe.payload.CardRequest;
import com.retro.retrobe.payload.CardResponse;
import com.retro.retrobe.repository.CardRepository;
import com.retro.retrobe.util.ModalMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {
    private CardRepository repository;

    @Autowired
    public CardService(final CardRepository cardRepository) {
        this.repository = cardRepository;
    }
    public CardResponse addNewUserCard(CardRequest request) {
        MemberCard memberCard = ModalMap.cardRequestToMemberCard(request);
        MemberCard savedMemberCard = repository.save(memberCard);
        return ModalMap.memberCardToCardResponse(savedMemberCard);
    }

    public List<CardResponse> getAllBoardCards(String boardName) {
        List<MemberCard> memberCards = repository.findByBoardName(boardName);
        List<CardResponse> cardResponses = new ArrayList<>();
        memberCards.forEach(cardMember -> {
            cardResponses.add(ModalMap.memberCardToCardResponse(cardMember));
        });

        return cardResponses;
    }
}
