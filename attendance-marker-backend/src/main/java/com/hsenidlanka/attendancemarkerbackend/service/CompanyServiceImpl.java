package com.hsenidlanka.attendancemarkerbackend.service;

import com.hsenidlanka.attendancemarkerbackend.dto.request.*;
import com.hsenidlanka.attendancemarkerbackend.dto.response.*;
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
    public PostCompanyRequest addCompany(CompanyRequest companyRequest) {
        try {
            logger.info("CompanyServiceImpl - addCompany()");
            String companyName = modelMapper.map(companyRequest, Company.class).getCompanyName();
            CompanyRequest companyObj = companyRepository.findByCompanyName(companyName);
            if (companyObj != null) {
                logger.error("CompanyServiceImpl - addCompany(Company already exists.)");
                return new PostCompanyRequest(null, 403);
            } else {
                companyRepository.save(modelMapper.map(companyRequest, Company.class));
                logger.info("CompanyServiceImpl - addCompany(Company added successfully.)");
                return new PostCompanyRequest(companyRequest, 201);
            }
        } catch (Exception exception) {
            return new PostCompanyRequest(null, 400);
        }
    }

    /**
     * Retrieve all registered companies
     **/
    public GetCompanyResponseList getAllCompanies() {
        try {
            logger.info("CompanyServiceImpl - getAllCompanies()");
            List<Company> companyList = companyRepository.findAll();
            if (companyList.isEmpty()) {
                logger.error("CompanyServiceImpl - getAllCompanies(No companies available)");
                return new GetCompanyResponseList(null, 404);
            } else {
                logger.info("CompanyServiceImpl - getAllCompanies(Companies are available)");
                return new GetCompanyResponseList(modelMapper.map(companyList, new TypeToken<List<CompanyResponse>>() {
                }.getType()), 200);
            }
        } catch (Exception exception) {
            return new GetCompanyResponseList(null, 400);
        }
    }

    /**
     * Retrieve company by company id
     **/
    public GetCompanyResponse getCompanyById(String id) {
        try {
            logger.info("CompanyServiceImpl - getCompanyById()");
            Optional<Company> companyObj = companyRepository.findById(id);
            if (companyObj.isPresent()) {
                logger.info("CompanyServiceImpl - getCompanyById(A company available with this ID)");
                Company company = companyObj.get();
                return new GetCompanyResponse(modelMapper.map(company, CompanyResponse.class),200);
            } else {
                logger.error("CompanyServiceImpl - getCompanyById(A company available with this ID)");
                logger.warn("No company available with this ID!");
                return new GetCompanyResponse(null,404);
            }
        } catch (Exception exception) {
            return new GetCompanyResponse(null,400);
        }
    }

    /**
     * Update company by company id
     **/
    public PutCompanyRequest updateCompanyById(String id, CompanyRequest companyRequest) {
        try {
            logger.info("CompanyServiceImpl - updateCompanyById()");
            Optional<Company> companyObj = companyRepository.findById(id);
            if (companyObj.isPresent()) {
                logger.info("CompanyServiceImpl - updateCompanyById(A company available with this ID)");
                Company company = companyObj.get();
                company.setCompanyName(companyRequest.getCompanyName());
                company.setCompanyLocation(companyRequest.getCompanyLocation());
                companyRepository.save(modelMapper.map(company, Company.class));
                return new PutCompanyRequest(companyRequest, 200);
            } else {
                logger.error("CompanyServiceImpl - updateCompanyById(No company available with this ID)");
                return new PutCompanyRequest(null, 404);
            }
        } catch (Exception exception) {
            return new PutCompanyRequest(null, 400);
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
                            return new MessageResponse("Unable to delete company!", 404);
                        }
                    } else {
                        logger.error("CompanyServiceImpl - deleteCompanyById(Company has registered users.)");
                        return new MessageResponse("Unable to delete company since it has registered users!", 400);
                    }
                } else {
                    logger.error("CompanyServiceImpl - deleteCompanyById(No company available with this ID.)");
                    return new MessageResponse(null, 404);
                }
            }


        } catch (Exception exception) {
            return new MessageResponse(null, 400);
        }
    }
}


