package com.hsenidlanka.attendancemarkerbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostCompanyRequest {
    private CompanyRequest companyRequest;
    private int statusCode;
}
