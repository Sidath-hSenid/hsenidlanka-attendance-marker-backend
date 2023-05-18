package com.hsenidlanka.attendancemarkerbackend.dto.response;

import com.hsenidlanka.attendancemarkerbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendanceResponse {
    private String id;
    private String date;
    private String startTime;
    private String endTime;
    private double workedHours;
    private User user;
}
