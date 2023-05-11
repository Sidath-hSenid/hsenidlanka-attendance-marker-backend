package com.hsenidlanka.attendancemarkerbackend.dto.request;

import com.hsenidlanka.attendancemarkerbackend.model.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {
    private String username;
    private String email;
    private Company company;
    private String password;
}
