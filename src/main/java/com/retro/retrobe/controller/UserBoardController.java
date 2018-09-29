package com.retro.retrobe.controller;

import com.retro.retrobe.exception.ResourceNotFoundException;
import com.retro.retrobe.model.UserBoard;
import com.retro.retrobe.payload.ApiResponse;
import com.retro.retrobe.payload.Board;
import com.retro.retrobe.payload.BoardResponse;
import com.retro.retrobe.payload.UserBoardRequest;
import com.retro.retrobe.repository.BoardRepository;
import com.retro.retrobe.security.CurrentUser;
import com.retro.retrobe.security.UserPrincipal;
import com.retro.retrobe.service.BoardService;
import com.retro.retrobe.util.ModalMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/")
public class UserBoardController {
    private static final Logger logger = LoggerFactory.getLogger(UserBoardController.class);
    private BoardService boardService;
    private BoardRepository repository;

    public UserBoardController(final BoardService boardService, final BoardRepository repository) {
        this.boardService = boardService;
        this.repository = repository;
    }

    @PostMapping("board")
    public BoardResponse saveUserBoard(@CurrentUser UserPrincipal currentUser, @RequestBody UserBoardRequest request) {
        try {
            return boardService.saveBoardInfo(request, currentUser);
        } catch (Exception e) {
            BoardResponse response = new BoardResponse();
            response.setErrorMessage(e.getCause().getCause().toString());
            return response;
        }
    }

    @GetMapping("board/{name}")
    public Board getSavedBoard(@PathVariable(value = "name") String name) {
        UserBoard savedBoard = repository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "Board name", name));
        logger.info("Saved Board  ", savedBoard);
        return ModalMap.mapToUserBoardToBoard(savedBoard);
    }

    @GetMapping("boards")
    public ApiResponse<?> getAllUserBoards(@CurrentUser UserPrincipal currentUser) {
        List<UserBoard> savedUserBoards = repository.findAll();
        List<Board> userOwnBoards = new ArrayList<>();
        List<Board> userMemberBoards = new ArrayList<>();
        Map<String, List<Board>> userBoardMap = new HashMap<>();
        savedUserBoards.forEach(board -> {
            if (board.getOwner() != null && (board.getOwner().getEmail()).equals(currentUser.getEmail())) {
                userOwnBoards.add(ModalMap.mapToUserBoardToBoard(board));
            }

            if (board.getBoardMembers() != null && !board.getBoardMembers().isEmpty()) {
                board.getBoardMembers().forEach(member -> {
                    if (!member.isBoardOwner() && member.getEmailAddress().equals(currentUser.getEmail())) {
                        userMemberBoards.add(ModalMap.mapToUserBoardToBoard(board));
                    }
                });
            }
        });

        if (!userOwnBoards.isEmpty()) {
            userBoardMap.put("userOwnBoards", userOwnBoards);
        }

        if (!userMemberBoards.isEmpty()) {
            userBoardMap.put("userMemberBoards", userMemberBoards);
        }

        return new ApiResponse<>(true, userBoardMap, "User Boards", 200);
    }

    @GetMapping("{name}/token")
    public ApiResponse<?> publishToken(@PathVariable(value = "name") String name) {
        if (repository.existsBoardByName(name)) {
            String token = UUID.randomUUID().toString();
            return new ApiResponse<>(true, token, "Generated Token", 200);
        }
        return new ApiResponse<>(false, "Board not exist", 400);
    }
}
