package com.hsenidlanka.attendancemarkerbackend.controller;

import com.hsenidlanka.attendancemarkerbackend.dto.request.UserRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.response.UserResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;
import com.hsenidlanka.attendancemarkerbackend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class UserController {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Retrieve all users
     **/
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> allUsers() {
        logger.info("UserController - allUsers()");
        return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
    }

    /**
     * Retrieve user by user ID
     **/
    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<UserResponse> userById(@PathVariable("id") String id) {
        logger.info("UserController - userById()");
        return new ResponseEntity(userService.getUserById(id), HttpStatus.OK);
    }

    /**
     * Retrieve users by company ID
     **/
    @GetMapping("/users/company-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> usersByCompanyId(@PathVariable("id") String id) {
        logger.info("UserController - usersByCompanyId()");
        return new ResponseEntity(userService.getUsersByCompanyId(id), HttpStatus.OK);
    }

    /**
     * Delete user by user ID
     **/
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable("id") String id) {
        logger.info("UserController - deleteUser()");
        return new ResponseEntity(userService.deleteUserById(id), HttpStatus.OK);
    }

    /**
     * Update user by user ID
     **/
    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserRequest> updateUser(@PathVariable("id") String id, @RequestBody UserRequest userRequest) {
        logger.info("UserController - updateUser()");
        return new ResponseEntity(userService.updateUserById(id, userRequest), HttpStatus.OK);
    }
}
