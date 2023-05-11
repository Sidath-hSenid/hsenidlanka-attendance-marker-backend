package com.hsenidlanka.attendancemarkerbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyResponse {
    private String id;
    private String companyName;
    private String companyLocation;
}
