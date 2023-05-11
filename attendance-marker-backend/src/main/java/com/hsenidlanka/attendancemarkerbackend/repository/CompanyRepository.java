package com.hsenidlanka.attendancemarkerbackend.repository;

import com.hsenidlanka.attendancemarkerbackend.dto.request.CompanyRequest;
import com.hsenidlanka.attendancemarkerbackend.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CompanyRepository extends MongoRepository<Company, String> {

    Optional<Company> findById(String id);
    CompanyRequest findByCompanyName(String companyName);
}
