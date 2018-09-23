package com.retro.retrobe.controller;

import com.retro.retrobe.payload.CardRequest;
import com.retro.retrobe.payload.CardResponse;
import com.retro.retrobe.repository.BoardRepository;
import com.retro.retrobe.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CardController {
    private static final Logger logger = LoggerFactory.getLogger(UserBoardController.class);

    private BoardRepository boardRepository;
    private CardService cardService;

    @Autowired
    public CardController(final BoardRepository boardRepository, final CardService cardService) {
        this.boardRepository = boardRepository;
        this.cardService = cardService;
    }

    @PostMapping("card")
    public CardResponse addNewCard(@RequestBody CardRequest request) {
        if (boardRepository.existsBoardByName(request.getBoardName())) {
            return cardService.addNewUserCard(request);
        }

        return null;
    }

    @GetMapping("cards/{name}")
    public List<CardResponse> getBoardCards(@PathVariable(value = "name") String name) {
        if (boardRepository.existsBoardByName(name)) {
            return cardService.getAllBoardCards(name);
        }

        return null;
    }

}
