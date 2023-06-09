package com.hsenidlanka.attendancemarkerbackend.dto.request;

import com.hsenidlanka.attendancemarkerbackend.model.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendanceUpdateEndTimeRequest {
    private String endTime;
    private double workedHours;
    private AttendanceStatus attendanceStatus;
}
