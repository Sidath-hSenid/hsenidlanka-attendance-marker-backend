package com.hsenidlanka.attendancemarkerbackend.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.hsenidlanka.attendancemarkerbackend.dto.response.LoginResponse;
import com.hsenidlanka.attendancemarkerbackend.service.RefreshTokenService;
import com.hsenidlanka.attendancemarkerbackend.utils.exception.TokenRefreshException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hsenidlanka.attendancemarkerbackend.model.ERole;
import com.hsenidlanka.attendancemarkerbackend.model.Role;
import com.hsenidlanka.attendancemarkerbackend.model.User;
import com.hsenidlanka.attendancemarkerbackend.dto.request.LoginRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.request.RegisterRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.response.JwtResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;
import com.hsenidlanka.attendancemarkerbackend.repository.RoleRepository;
import com.hsenidlanka.attendancemarkerbackend.repository.UserRepository;
import com.hsenidlanka.attendancemarkerbackend.utils.jwt.JwtUtils;
import com.hsenidlanka.attendancemarkerbackend.service.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * User login
     **/
    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("AuthController - authenticateUser()");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new LoginResponse((new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles)), 200);
    }

    /**
     * Register new admin
     **/
    @PostMapping("/add")
    public MessageResponse addUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        logger.info("AuthController - registerUser()");
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            logger.error("AuthController - registerUser(User name already taken.)");
            return new MessageResponse("Error: Username is already taken!", 403);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            logger.error("AuthController - registerUser(Email already taken.)");
            return new MessageResponse("Error: Email is already in use!",403);
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getCompany());

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            logger.error("AuthController - registerUser(User role is not found.)");
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return new MessageResponse("User registered successfully!", 200);
    }

    /**
     * Register new user
     **/
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        logger.info("AuthController - registerUser()");
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            logger.error("AuthController - registerUser(User name already taken.)");
            return new MessageResponse("Error: Username is already taken!", 403);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            logger.error("AuthController - registerUser(Email already taken.)");
            return new MessageResponse("Error: Email is already in use!",403);
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getCompany());

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            logger.error("AuthController - registerUser(User role is not found.)");
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return new MessageResponse("User registered successfully!", 200);
    }

    /**
     * User log out
     **/
    @PostMapping("/log-out")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> logoutUser() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principle.toString() != "anonymousUser") {
            String userId = ((UserDetailsImpl) principle).getId();
            refreshTokenService.deleteByUserId(userId);
        }

        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new MessageResponse("You've been signed out!", 200));
    }

//    @PostMapping("/refresh-token")
//    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
//        String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);
//
//        if ((refreshToken != null) && (refreshToken.length() > 0)) {
//            return refreshTokenService.findByToken(refreshToken)
//                    .map(refreshTokenService::verifyExpiration)
//                    .map(RefreshToken::getUser)
//                    .map(user -> {
//                        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);
//
//                        return ResponseEntity.ok()
//                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//                                .body(new MessageResponse("Token is refreshed successfully!", 200));
//                    })
//                    .orElseThrow(() -> new TokenRefreshException(refreshToken,
//                            "Refresh token is not in database!"));
//        }
//
//        return ResponseEntity.badRequest().body(new MessageResponse("Refresh Token is empty!", 400));
//    }

}