package com.hsenidlanka.attendancemarkerbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendanceUpdateEndTimeRequest {
    private String endTime;
    private double workedHours;
    private Boolean halfDay;
}
