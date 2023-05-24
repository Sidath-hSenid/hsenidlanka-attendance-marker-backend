package com.hsenidlanka.attendancemarkerbackend.controller;

import com.hsenidlanka.attendancemarkerbackend.dto.request.CompanyRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.response.CompanyResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;
import com.hsenidlanka.attendancemarkerbackend.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    Logger logger = LoggerFactory.getLogger(CompanyController.class);

    /**
     * Add new company
     **/
    @PostMapping("/companies")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyRequest> addCompany(@RequestBody CompanyRequest companyRequest) {
        logger.info("CompanyController - addCompany()");
        return new ResponseEntity(companyService.addCompany(companyRequest), HttpStatus.CREATED);
    }

    /**
     * Retrieve all registered companies
     **/
    @GetMapping("/companies")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CompanyResponse>> allCompanies() {
        logger.info("CompanyController - allCompanies()");
        return new ResponseEntity(companyService.getAllCompanies(), HttpStatus.OK);
    }

    /**
     * Retrieve a company by company ID
     **/
    @GetMapping("/companies/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyResponse> companyById(@PathVariable("id") String id) {
        logger.info("CompanyController - companyById()");
        return new ResponseEntity(companyService.getCompanyById(id), HttpStatus.OK);
    }

    /**
     * Delete a company by company ID
     **/
    @DeleteMapping("/companies/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteCompany(@PathVariable("id") String id) {
        logger.info("CompanyController - deleteCompany()");
        return new ResponseEntity(companyService.deleteCompanyById(id), HttpStatus.OK);
    }

    /**
     * Update a company by company ID
     **/
    @PutMapping("/companies/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyRequest> updateCompany(@PathVariable("id") String id, @RequestBody CompanyRequest companyRequest) {
        logger.info("CompanyController - updateCompany()");
        return new ResponseEntity(companyService.updateCompanyById(id, companyRequest), HttpStatus.OK);
    }
}
