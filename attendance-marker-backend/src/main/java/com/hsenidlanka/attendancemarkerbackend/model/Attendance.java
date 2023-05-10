package com.hsenidlanka.attendancemarkerbackend.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "attendances")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Attendance {
    @Id @Generated private String Id;
    @NotBlank private String date;
    @NotBlank private String startTime;
    @NotBlank private String endTime;
    @NotBlank private long workedHours;
    @NotBlank private User user;

    public Attendance(String date, String startTime, String endTime, long workedHours, User user){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workedHours = workedHours;
        this.user = user;
    }
}
