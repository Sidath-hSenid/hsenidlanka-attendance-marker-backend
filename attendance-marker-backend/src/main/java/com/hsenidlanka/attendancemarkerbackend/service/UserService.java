package com.hsenidlanka.attendancemarkerbackend.service;

import com.hsenidlanka.attendancemarkerbackend.dto.request.PutUserRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.request.ResetPasswordRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.request.UserRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.response.GetUserResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.GetUserResponseList;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;

import java.util.List;

public interface UserService {
    GetUserResponseList getAllUsers();

    GetUserResponse getUserById(String id);

    MessageResponse deleteUserById(String id);

    PutUserRequest updateUserById(String id, UserRequest userRequest);

    GetUserResponseList getUsersByCompanyId(String id);

    MessageResponse resetPassword(String username, String email, ResetPasswordRequest resetPasswordRequest);

}
