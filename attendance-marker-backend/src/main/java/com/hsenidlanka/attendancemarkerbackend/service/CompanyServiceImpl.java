package com.hsenidlanka.attendancemarkerbackend.service;

import com.hsenidlanka.attendancemarkerbackend.dto.request.CompanyRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.response.CompanyResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;
import com.hsenidlanka.attendancemarkerbackend.model.Company;
import com.hsenidlanka.attendancemarkerbackend.repository.CompanyRepository;
import com.hsenidlanka.attendancemarkerbackend.utils.exception.HandleException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Override
    public CompanyRequest addCompany(CompanyRequest companyRequest) {
        try{
            logger.info("Inside addCompany() function...");
            String companyName = modelMapper.map(companyRequest, Company.class).getCompanyName();
            CompanyRequest companyObj = companyRepository.findByCompanyName(companyName);
            if(companyObj != null){
                logger.warn("Company already exists!");
                throw new HandleException("Exits without inserting data!!");
            }else{
                companyRepository.save(modelMapper.map(companyRequest, Company.class));
                logger.info("Company added...");
                return companyRequest;
            }
        }
        catch(Exception exception){
            throw exception;
        }
    }

    public List<CompanyResponse> getAllCompanies(){
        try{
            logger.info("Inside getAllCompanies() function...");
            List<Company> companyList = companyRepository.findAll();
            if(companyList.isEmpty()){
                logger.warn("No companies available!");
                throw new HandleException("Exits without displaying data");
            }
            else{
                logger.info("Companies are available..." + companyList);
                return modelMapper.map(companyList, new TypeToken<List<CompanyResponse>>(){}.getType());
            }
        }catch(Exception exception){
            throw exception;
        }
    }

    public CompanyResponse getCompanyById(String id){
        try{
            logger.info("Inside getCompanyById() function...");
            Optional<Company> companyObj = companyRepository.findById(id);
            if(companyObj.isPresent()){
                logger.info("A company available with this ID!" + companyObj);
                Company company = companyObj.get();
                return modelMapper.map(company, CompanyResponse.class);
            }else{
                logger.warn("No company available with this ID!");
                throw new HandleException("Exits without displaying data");
            }
        }catch(Exception exception){
            throw exception;
        }
    }

    public CompanyRequest updateCompanyById(String id, CompanyRequest companyRequest){
        try{
            logger.info("Inside updateCompanyById() function...");
            Optional<Company> companyObj = companyRepository.findById(id);
            if(companyObj.isPresent()){
                logger.info("A company available with this ID!" + companyObj);
                Company company = companyObj.get();
                company.setCompanyName(companyRequest.getCompanyName());
                company.setCompanyLocation(companyRequest.getCompanyLocation());
                companyRepository.save(modelMapper.map(company, Company.class));
                logger.info("Company updated...");
                return companyRequest;
            }else{
                logger.warn("No company available with this ID!");
                throw new HandleException("Exits without updating data");
            }
        }catch(Exception exception){
            throw exception;
        }
    }

    public MessageResponse deleteCompanyById(String id){
        try {
            logger.info("Inside deleteCompanyById() function...");
            Optional<Company> companyObj = companyRepository.findById(id);
            if(companyObj.isPresent()){
                logger.warn("A company available with this ID!" + companyObj);
                companyRepository.deleteById(id);
                return new MessageResponse("Company deleted successfully!");
            }else{
                logger.warn("No company available with this ID!");
                throw new HandleException("Exits without deleting data");
            }
        }catch(Exception exception){
            throw exception;
        }
    }
}
