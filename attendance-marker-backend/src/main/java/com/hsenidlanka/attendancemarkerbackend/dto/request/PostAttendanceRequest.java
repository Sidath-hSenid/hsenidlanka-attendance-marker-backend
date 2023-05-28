package com.hsenidlanka.attendancemarkerbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostAttendanceRequest {
    private AttendanceRequest attendanceRequest;
    private int statCode;
}
