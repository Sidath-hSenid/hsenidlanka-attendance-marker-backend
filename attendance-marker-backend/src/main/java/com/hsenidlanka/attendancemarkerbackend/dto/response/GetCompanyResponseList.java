package com.hsenidlanka.attendancemarkerbackend.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetCompanyResponseList {
    private List<CompanyResponse> companyResponseList;
    private int statusCode;
}