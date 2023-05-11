package com.hsenidlanka.attendancemarkerbackend.dto.request;

import java.util.Set;

import com.hsenidlanka.attendancemarkerbackend.model.Company;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> roles;

    private Company company;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}