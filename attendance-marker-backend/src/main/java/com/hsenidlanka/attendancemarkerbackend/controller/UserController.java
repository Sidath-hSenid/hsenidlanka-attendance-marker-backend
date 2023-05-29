package com.hsenidlanka.attendancemarkerbackend.controller;

import com.hsenidlanka.attendancemarkerbackend.dto.request.*;
import com.hsenidlanka.attendancemarkerbackend.dto.response.*;
import com.hsenidlanka.attendancemarkerbackend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Retrieve all users
     **/
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public GetUserResponseList allUsers() {
        logger.info("UserController - allUsers()");
        return userService.getAllUsers();
    }

    /**
     * Retrieve user by user ID
     **/
    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public GetUserResponse userById(@PathVariable("id") String id) {
        logger.info("UserController - userById()");
        return userService.getUserById(id);
    }

    /**
     * Retrieve users by company ID
     **/
    @GetMapping("/users/company-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GetUserResponseList usersByCompanyId(@PathVariable("id") String id) {
        logger.info("UserController - usersByCompanyId()");
        return userService.getUsersByCompanyId(id);
    }

    /**
     * Delete user by user ID
     **/
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse deleteUser(@PathVariable("id") String id) {
        logger.info("UserController - deleteUser()");
        return userService.deleteUserById(id);
    }

    /**
     * Update user by user ID
     **/
    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PutUserRequest updateUser(@PathVariable("id") String id, @RequestBody UserRequest userRequest) {
        logger.info("UserController - updateUser()");
        return userService.updateUserById(id, userRequest);
    }

    /**
     * Reset password by username and email
     **/
    @PutMapping("/users/reset-password/{username}/{email}")
    public MessageResponse resetPassword(@PathVariable("username") String username, @PathVariable("email") String email, @RequestBody ResetPasswordRequest resetPasswordRequest) {
        logger.info("UserController - resetPassword()");
        return userService.resetPassword(username, email, resetPasswordRequest);
    }

}
