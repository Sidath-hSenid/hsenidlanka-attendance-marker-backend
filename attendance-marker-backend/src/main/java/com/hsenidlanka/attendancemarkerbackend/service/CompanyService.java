package com.hsenidlanka.attendancemarkerbackend.service;

import com.hsenidlanka.attendancemarkerbackend.dto.request.*;
import com.hsenidlanka.attendancemarkerbackend.dto.response.GetCompanyResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.GetCompanyResponseList;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;

import java.util.List;

public interface CompanyService {
    PostCompanyRequest addCompany(CompanyRequest companyRequest);

    GetCompanyResponseList getAllCompanies();

    GetCompanyResponse getCompanyById(String id);

    MessageResponse deleteCompanyById(String id);

    PutCompanyRequest updateCompanyById(String id, CompanyRequest companyRequest);
}
