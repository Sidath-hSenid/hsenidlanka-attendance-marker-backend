package com.hsenidlanka.attendancemarkerbackend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class UserController {
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
}
