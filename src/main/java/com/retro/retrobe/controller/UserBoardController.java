package com.retro.retrobe.controller;

import com.retro.retrobe.exception.AppException;
import com.retro.retrobe.exception.ResourceNotFoundException;
import com.retro.retrobe.model.*;
import com.retro.retrobe.payload.*;
import com.retro.retrobe.repository.BoardRepository;
import com.retro.retrobe.repository.RoleRepository;
import com.retro.retrobe.repository.UserRepository;
import com.retro.retrobe.security.CurrentUser;
import com.retro.retrobe.security.UserPrincipal;
import com.retro.retrobe.service.BoardService;
import com.retro.retrobe.service.EmailService;
import com.retro.retrobe.util.AppUtil;
import com.retro.retrobe.util.ModalMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/")
public class UserBoardController {
    private static final Logger logger = LoggerFactory.getLogger(UserBoardController.class);
    private BoardService boardService;
    private BoardRepository repository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private EmailService emailService;

    public UserBoardController(final BoardService boardService,
                               final BoardRepository repository,
                               final UserRepository userRepository,
                               final RoleRepository roleRepository,
                               final EmailService emailService) {
        this.boardService = boardService;
        this.repository = repository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
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

    @PostMapping("{name}/manageMembers")
    public void addNewBoardMember(@PathVariable(value = "name") String name, @RequestBody Member member, HttpServletRequest request) {
        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":4200";
        String[] emails = member.getEmailAddress().split(",");
        if (repository.existsBoardByName(name)) {
            UserBoard userBoard = repository.findByName(name)
                    .orElseThrow(() -> new ResourceNotFoundException("Board", "Board name", name));
            for (String email : emails) {
                User userInfo = userRepository.findUserByEmail(email);
                if (userInfo != null) {
                    userBoard.setName(name);
                    BoardMember boardMember = new BoardMember();
                    String userFullName = AppUtil.getUserFullName(userInfo.getFirstName(), userInfo.getLastName());
                    String abbreviation = AppUtil.abbreviation(userFullName);
                    boardMember.setFullName(userFullName);
                    boardMember.setLastName(userInfo.getLastName());
                    boardMember.setFirstName(userInfo.getFirstName());
                    boardMember.setBoardOwner(false);
                    boardMember.setActive(userInfo.isEnabled());
                    boardMember.setAbbreviation(abbreviation);
                    boardMember.setEmailAddress(member.getEmailAddress());
                    boardMember.setCanContribute(member.isCanContribute());
                    userBoard.addBoardMember(boardMember);
                    try {
                        repository.save(userBoard);
                        // repository.saveBoardByName(name, boardMember);
                    } catch (DataIntegrityViolationException e) {
                        logger.error(e.getMessage());
                    }

                    registrationEmail.setTo(email);
                    registrationEmail.setSubject("Invitation Board Member");
                    registrationEmail.setText("To confirm your board request, please click the link below:\n"
                            + appUrl + "/boards/board/" + name);

                    emailService.sendEmail(registrationEmail);
                } else {
                    User user = new User();
                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new AppException("User Role not set."));

                    user.setRoles(Collections.singleton(userRole));
                    user.setEmail(email);
                    user.setEnabled(false);
                    user.setConfirmationToken(UUID.randomUUID().toString());
                    User savedUser = userRepository.save(user);

                    BoardMember boardMember = new BoardMember();
                    boardMember.setActive(savedUser.isEnabled());
                    boardMember.setEmailAddress(email);
                    userBoard.addBoardMember(boardMember);
                    try {
                        repository.save(userBoard);
                    } catch (DataIntegrityViolationException e) {
                        logger.error(e.getMessage());
                    }
                    sendMailToBoardMember(savedUser, name, appUrl);
                }
            }
        }
    }

    @DeleteMapping("{name}/manageMembers/{id}")
    public void deleteMember(@PathVariable(value = "name") String name, @PathVariable(value = "id") Long id) {
        UserBoard userBoard = repository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "Board name", name));
        BoardMember boardMember = new BoardMember();
        boardMember.setId(id);
        userBoard.removeBoardMember(boardMember);
        repository.save(userBoard);

    }

    @GetMapping("{name}/contactList")
    public ApiResponse<?> getBoardContactList(@PathVariable(value = "name") String name) {
        UserBoard userBoard = repository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "Board name", name));
        List<Member> boardMembers = ModalMap.boardMemberToMember(userBoard.getBoardMembers());
        return new ApiResponse<>(true, boardMembers, "success", 200);
    }

    @PostMapping("{name}/sendMailToInActiveMember")
    public void sendMailToMember(@PathVariable(value = "name") String name, @RequestBody Member member, HttpServletRequest request) {
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":4200";
        User userInfo = userRepository.findUserByEmail(member.getEmailAddress());
        sendMailToBoardMember(userInfo, name, appUrl);
    }

    private void sendMailToBoardMember(User user, String name, String appUrl) {
        SimpleMailMessage registrationEmail = new SimpleMailMessage();

        registrationEmail.setTo(user.getEmail());
        registrationEmail.setSubject("Registration Confirmation");
        registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                + appUrl + "/auth/register?token=" + user.getConfirmationToken() + "&boardName=" + name);

        emailService.sendEmail(registrationEmail);
    }
}
