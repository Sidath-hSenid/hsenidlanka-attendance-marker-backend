package com.hsenidlanka.attendancemarkerbackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "companies")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Company {
    @Id @Generated private String id;
    private String companyName;
    private String companyLocation;
}
