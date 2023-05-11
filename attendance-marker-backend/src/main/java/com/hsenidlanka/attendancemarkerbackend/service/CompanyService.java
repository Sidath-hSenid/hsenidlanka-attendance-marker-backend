package com.hsenidlanka.attendancemarkerbackend.service;

import com.hsenidlanka.attendancemarkerbackend.dto.request.CompanyRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.response.CompanyResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompanyService {
    CompanyRequest addCompany(CompanyRequest companyRequest);

    List<CompanyResponse> getAllCompanies();

    CompanyResponse getCompanyById(String id);

    MessageResponse deleteCompanyById(String id);

    CompanyRequest updateCompanyById(String id, CompanyRequest companyRequest);
}
