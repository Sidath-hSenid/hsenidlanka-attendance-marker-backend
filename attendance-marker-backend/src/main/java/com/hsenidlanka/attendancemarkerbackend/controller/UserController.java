package com.hsenidlanka.attendancemarkerbackend.controller;

import com.hsenidlanka.attendancemarkerbackend.dto.request.UserRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.response.UserResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;
import com.hsenidlanka.attendancemarkerbackend.service.UserService;
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

    @GetMapping("/home")
    public String allAccess() {
        return "This is home page";
    }

    @GetMapping("/user-home")
    @PreAuthorize("hasRole('USER')")
    public String userAccess() {
        return "This is user page";
    }

    @GetMapping("/admin-home")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "This is admin page";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> allCompanies(){
        return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> userById(@PathVariable("id")  String id){
        return new ResponseEntity(userService.getUserById(id), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable("id")  String id){
        return new ResponseEntity(userService.deleteUserById(id), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserRequest> updateUser(@PathVariable("id")  String id, @RequestBody UserRequest userRequest){
        return new ResponseEntity(userService.updateUserById(id, userRequest), HttpStatus.OK);
    }

    @GetMapping("/users-by-company/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> allUsersByCompanyId(@PathVariable("id")  String id){
        return new ResponseEntity(userService.getUsersByCompanyId(id), HttpStatus.OK);
    }
}
