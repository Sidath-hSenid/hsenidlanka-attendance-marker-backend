package com.hsenidlanka.attendancemarkerbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PutAttendanceUpdateRequestById {
    private AttendanceUpdateRequestById attendanceUpdateRequestById;
    private int statusCode;
}
