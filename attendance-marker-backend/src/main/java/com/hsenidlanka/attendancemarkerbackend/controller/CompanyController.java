package com.hsenidlanka.attendancemarkerbackend.controller;

import com.hsenidlanka.attendancemarkerbackend.dto.request.*;
import com.hsenidlanka.attendancemarkerbackend.dto.response.*;
import com.hsenidlanka.attendancemarkerbackend.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public PostCompanyRequest addCompany(@RequestBody CompanyRequest companyRequest) {
        logger.info("CompanyController - addCompany()");
        return companyService.addCompany(companyRequest);
    }

    /**
     * Retrieve all registered companies
     **/
    @GetMapping("/companies")
    @PreAuthorize("hasRole('ADMIN')")
    public GetCompanyResponseList allCompanies() {
        logger.info("CompanyController - allCompanies()");
        return companyService.getAllCompanies();
    }

    /**
     * Retrieve a company by company ID
     **/
    @GetMapping("/companies/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GetCompanyResponse companyById(@PathVariable("id") String id) {
        logger.info("CompanyController - companyById()");
        return companyService.getCompanyById(id);
    }

    /**
     * Delete a company by company ID
     **/
    @DeleteMapping("/companies/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse deleteCompany(@PathVariable("id") String id) {
        logger.info("CompanyController - deleteCompany()");
        return companyService.deleteCompanyById(id);
    }

    /**
     * Update a company by company ID
     **/
    @PutMapping("/companies/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PutCompanyRequest updateCompany(@PathVariable("id") String id, @RequestBody CompanyRequest companyRequest) {
        logger.info("CompanyController - updateCompany()");
        return companyService.updateCompanyById(id, companyRequest);
    }
}
