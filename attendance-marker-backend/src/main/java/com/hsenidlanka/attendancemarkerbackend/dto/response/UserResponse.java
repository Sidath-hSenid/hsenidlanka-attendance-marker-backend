package com.hsenidlanka.attendancemarkerbackend.dto.response;

import com.hsenidlanka.attendancemarkerbackend.model.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private Company company;
    private Set<String> roles;
}
