package com.hsenidlanka.attendancemarkerbackend.service;

import com.hsenidlanka.attendancemarkerbackend.dto.request.CompanyRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.response.CompanyResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.UserResponse;
import com.hsenidlanka.attendancemarkerbackend.model.Company;
import com.hsenidlanka.attendancemarkerbackend.model.User;
import com.hsenidlanka.attendancemarkerbackend.repository.CompanyRepository;
import com.hsenidlanka.attendancemarkerbackend.repository.UserRepository;
import com.hsenidlanka.attendancemarkerbackend.utils.exception.HandleException;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    /**
     * Add new company
     **/
    @Override
    public CompanyRequest addCompany(CompanyRequest companyRequest) {
        try {
            logger.info("CompanyServiceImpl - addCompany()");
            String companyName = modelMapper.map(companyRequest, Company.class).getCompanyName();
            CompanyRequest companyObj = companyRepository.findByCompanyName(companyName);
            if (companyObj != null) {
                logger.error("CompanyServiceImpl - addCompany(Company already exists.)");
                throw new HandleException("Exits without inserting data!!");
            } else {
                companyRepository.save(modelMapper.map(companyRequest, Company.class));
                logger.info("CompanyServiceImpl - addCompany(Company added successfully.)");
                return companyRequest;
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * Retrieve all registered companies
     **/
    public List<CompanyResponse> getAllCompanies() {
        try {
            logger.info("CompanyServiceImpl - getAllCompanies()");
            List<Company> companyList = companyRepository.findAll();
            if (companyList.isEmpty()) {
                logger.error("CompanyServiceImpl - getAllCompanies(No companies available)");
                throw new HandleException("Exits without displaying data");
            } else {
                logger.info("CompanyServiceImpl - getAllCompanies(Companies are available)");
                return modelMapper.map(companyList, new TypeToken<List<CompanyResponse>>() {
                }.getType());
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * Retrieve company by company id
     **/
    public CompanyResponse getCompanyById(String id) {
        try {
            logger.info("CompanyServiceImpl - getCompanyById()");
            Optional<Company> companyObj = companyRepository.findById(id);
            if (companyObj.isPresent()) {
                logger.info("CompanyServiceImpl - getCompanyById(A company available with this ID)");
                Company company = companyObj.get();
                return modelMapper.map(company, CompanyResponse.class);
            } else {
                logger.error("CompanyServiceImpl - getCompanyById(A company available with this ID)");
                logger.warn("No company available with this ID!");
                throw new HandleException("Exits without displaying data");
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * Update company by company id
     **/
    public CompanyRequest updateCompanyById(String id, CompanyRequest companyRequest) {
        try {
            logger.info("CompanyServiceImpl - updateCompanyById()");
            Optional<Company> companyObj = companyRepository.findById(id);
            if (companyObj.isPresent()) {
                logger.info("CompanyServiceImpl - updateCompanyById(A company available with this ID)");
                Company company = companyObj.get();
                company.setCompanyName(companyRequest.getCompanyName());
                company.setCompanyLocation(companyRequest.getCompanyLocation());
                companyRepository.save(modelMapper.map(company, Company.class));
                return companyRequest;
            } else {
                logger.error("CompanyServiceImpl - updateCompanyById(No company available with this ID)");
                throw new HandleException("Exits without updating data");
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * Delete company by company id
     **/
    public MessageResponse deleteCompanyById(String id) {
        try {
            logger.info("CompanyServiceImpl - deleteCompanyById()");
            Optional<Company> companyObj = companyRepository.findById(id);
            List<UserResponse> userResponseList = new ArrayList<>();
            List<User> userList = userRepository.findAll();
            List<UserResponse> userCompanyResponseList = new ArrayList<>();
            logger.info(userList.toString());
            if (userList.isEmpty()) {
                logger.error("CompanyServiceImpl - deleteCompanyById(No users available)");
                throw new HandleException("Exits without displaying data");
            } else {
                userList.forEach(user -> {
                            user.getRoles().stream().filter(role -> {
                                if (role.getName().name().equals("ROLE_USER")) {
                                    logger.info("CompanyServiceImpl - deleteCompanyById(Users are available)");
                                    UserResponse userResponse = new UserResponse();
                                    userResponse.setId(user.getId());
                                    userResponse.setUsername(user.getUsername());
                                    userResponse.setEmail(user.getEmail());
                                    userResponse.setCompany(user.getCompany());
                                    userResponseList.add(userResponse);
                                    return true;
                                } else {
                                    return false;
                                }
                            }).collect(Collectors.toList());
                        }
                );
                if (!userResponseList.isEmpty()) {
                    logger.info("CompanyServiceImpl - deleteCompanyById(userResponseList not empty)");
                    userResponseList.stream().filter(user -> {
                        System.out.println();
                        if (user.getCompany().getId().equals(id)) {
                            logger.info("CompanyServiceImpl - deleteCompanyById(Users available with this company ID)");
                            UserResponse userResponse = new UserResponse();
                            userResponse.setId(user.getId());
                            userResponse.setUsername(user.getUsername());
                            userResponse.setEmail(user.getEmail());
                            userResponse.setCompany(user.getCompany());
                            userCompanyResponseList.add(userResponse);
                            return true;
                        } else {
                            return false;
                        }
                    }).collect(Collectors.toList());
                    if (userCompanyResponseList.isEmpty()) {
                        if (companyObj.isPresent()) {
                            logger.info("CompanyServiceImpl - deleteCompanyById(companyObj presents.)");
                            companyRepository.deleteById(id);
                            return new MessageResponse("Company Deleted Successfully!", 200);
                        } else {
                            logger.error("CompanyServiceImpl - deleteCompanyById(companyObj not presents.)");
                            return new MessageResponse("Unable to delete company!", 400);
                        }
                    } else {
                        logger.error("CompanyServiceImpl - deleteCompanyById(Company has registered users.)");
                        return new MessageResponse("Unable to delete company since it has registered users!", 400);
                    }
                } else {
                    logger.error("CompanyServiceImpl - deleteCompanyById(No company available with this ID.)");
                    throw new HandleException("Exits without displaying data");
                }
            }


        } catch (Exception exception) {
            throw exception;
        }
    }
}


