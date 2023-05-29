package com.hsenidlanka.attendancemarkerbackend.service;

import com.hsenidlanka.attendancemarkerbackend.dto.request.LoginRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.request.RegisterRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.response.LoginResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {

    LoginResponse authenticateUser(LoginRequest loginRequest);

    MessageResponse addUser(RegisterRequest signUpRequest);

    MessageResponse registerUser(RegisterRequest signUpRequest);

    ResponseEntity<?> logoutUser();

}
