package com.hsenidlanka.attendancemarkerbackend.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "attendances")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Attendance {
    @Id @Generated private String id;
    @NotBlank private String date;
    @NotBlank private String startTime;
    private String endTime;
    private double workedHours;
    @NotBlank private AttendanceStatus attendanceStatus;
    @DBRef @NotBlank private User user;

    public Attendance(String date, String startTime, String endTime, double workedHours,AttendanceStatus attendanceStatus, User user){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workedHours = workedHours;
        this.attendanceStatus = attendanceStatus;
        this.user = user;
    }
}
