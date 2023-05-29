package com.hsenidlanka.attendancemarkerbackend.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.hsenidlanka.attendancemarkerbackend.dto.response.LoginResponse;
import com.hsenidlanka.attendancemarkerbackend.service.AuthService;
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
    AuthService authService;

    @Autowired
    RefreshTokenService refreshTokenService;

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * User login
     **/
    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("AuthController - authenticateUser()");
        return authService.authenticateUser(loginRequest);
    }

    /**
     * Register new admin
     **/
    @PostMapping("/add")
    public MessageResponse addUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        logger.info("AuthController - registerUser()");
        return authService.addUser(signUpRequest);
    }

    /**
     * Register new user
     **/
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        logger.info("AuthController - registerUser()");
        return authService.registerUser(signUpRequest);
    }

    /**
     * User log out
     **/
    @PostMapping("/log-out")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> logoutUser() {
        logger.info("AuthController - logoutUser()");
        return authService.logoutUser();
    }
}