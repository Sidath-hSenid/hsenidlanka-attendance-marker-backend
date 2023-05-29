package com.hsenidlanka.attendancemarkerbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class LoginResponse {
    private JwtResponse jwtResponse;
    private int statusCode;
}
