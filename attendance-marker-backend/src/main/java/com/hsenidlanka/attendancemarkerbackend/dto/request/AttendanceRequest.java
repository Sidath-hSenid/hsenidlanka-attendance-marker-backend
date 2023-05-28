package com.hsenidlanka.attendancemarkerbackend.dto.request;

import com.hsenidlanka.attendancemarkerbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendanceRequest {
     private String date;
     private String startTime;
     private String endTime;
     private double workedHours;
     private Boolean halfDay;
     private User user;
}
