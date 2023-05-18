package com.hsenidlanka.attendancemarkerbackend.controller;

import com.hsenidlanka.attendancemarkerbackend.dto.request.CompanyRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.response.CompanyResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;
import com.hsenidlanka.attendancemarkerbackend.service.CompanyService;
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

    @PostMapping("/companies")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyRequest> addCompany(@RequestBody CompanyRequest companyRequest){
        return new ResponseEntity(companyService.addCompany(companyRequest), HttpStatus.CREATED);
    }

    @GetMapping("/companies")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CompanyResponse>> allCompanies(){
        return new ResponseEntity(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @GetMapping("/companies/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyResponse> companyById(@PathVariable("id")  String id){
        return new ResponseEntity(companyService.getCompanyById(id), HttpStatus.OK);
    }

    @DeleteMapping("/companies/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteCompany(@PathVariable("id")  String id){
        return new ResponseEntity(companyService.deleteCompanyById(id), HttpStatus.OK);
    }

    @PutMapping("/companies/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyRequest> updateCompany(@PathVariable("id")  String id,@RequestBody CompanyRequest companyRequest){
        return new ResponseEntity(companyService.updateCompanyById(id, companyRequest), HttpStatus.OK);
    }
}
