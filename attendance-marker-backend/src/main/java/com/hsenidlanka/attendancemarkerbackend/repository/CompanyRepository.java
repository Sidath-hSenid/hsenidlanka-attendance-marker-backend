package com.hsenidlanka.attendancemarkerbackend.repository;

import com.hsenidlanka.attendancemarkerbackend.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {

}
