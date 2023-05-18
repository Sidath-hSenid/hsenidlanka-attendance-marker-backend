package com.hsenidlanka.attendancemarkerbackend.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "companies")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Company {
    @Id @Generated private String id;
    @NotBlank private String companyName;
    @NotBlank private String companyLocation;
}
