package com.hsenidlanka.attendancemarkerbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetAttendanceResponse {
    private AttendanceResponse attendanceResponse;
    private int statusCode;
}
