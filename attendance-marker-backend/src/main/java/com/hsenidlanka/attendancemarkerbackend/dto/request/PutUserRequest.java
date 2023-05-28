package com.hsenidlanka.attendancemarkerbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PutUserRequest {
    private UserRequest userRequest;
    private int statusCode;
}
