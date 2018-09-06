package com.retro.retrobe.controller;

import com.retro.retrobe.exception.AppException;
import com.retro.retrobe.model.Role;
import com.retro.retrobe.model.RoleName;
import com.retro.retrobe.model.User;
import com.retro.retrobe.payload.ApiResponse;
import com.retro.retrobe.payload.JwtAuthenticationResponse;
import com.retro.retrobe.payload.LoginRequest;
import com.retro.retrobe.payload.SignUpRequest;
import com.retro.retrobe.repository.RoleRepository;
import com.retro.retrobe.repository.UserRepository;
import com.retro.retrobe.security.CustomUserDetailsService;
import com.retro.retrobe.security.JwtTokenProvider;
import com.retro.retrobe.service.EmailService;
import com.retro.retrobe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider tokenProvider;

    private EmailService emailService;

    private UserService userService;


    @Autowired
    public AuthController(final AuthenticationManager authenticationManager,
                          final UserRepository userRepository,
                          final RoleRepository roleRepository,
                          final JwtTokenProvider tokenProvider,
                          final PasswordEncoder passwordEncoder,
                          final EmailService emailService,
                          final UserService userService,
                          final CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {
        /*if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }*/

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!", 200),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        /*User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(), false, );*/
        User user = new User();

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));
        user.setEmail(signUpRequest.getEmail());
        user.setEnabled(false);
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setConfirmationToken(UUID.randomUUID().toString());
        userRepository.save(user);

        /*URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();*/
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":4200";

        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(user.getEmail());
        registrationEmail.setSubject("Registration Confirmation");
        registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                + appUrl + "/register?token=" + user.getConfirmationToken());

        emailService.sendEmail(registrationEmail);

        return new ResponseEntity<>(new ApiResponse<>(true,"Email sent, please confirm", 200), HttpStatus.OK);


        // https://www.codebyamir.com/blog/user-account-registration-with-spring-boot
    }

    @GetMapping("register")
    public ResponseEntity<?> registerConfirmation(@RequestParam("token") String token) {
        User user = userService.findByConfirmationToken(token);
        if(user != null) {
            return new ResponseEntity<>(new ApiResponse<>(true, user,"please complete registration", 200), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse<>(false,"token not found", 400), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("register")
    public ResponseEntity<?> processConfirmationForm(@Valid @RequestBody SignUpRequest signUpRequest) {
        // Zxcvbn passwordCheck = new Zxcvbn();
        //Strength strength = passwordCheck.measure(requestParams.get("password"));
        User user = userRepository.findUserByEmail(signUpRequest.getEmail());
        if(user != null) {
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            user.setConfirmationToken("");
            userRepository.save(user);
            return new ResponseEntity<>(new ApiResponse(true, "successfully confirmed, please login", 200), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "please confirm token", 400), HttpStatus.BAD_REQUEST);
        }
    }
}