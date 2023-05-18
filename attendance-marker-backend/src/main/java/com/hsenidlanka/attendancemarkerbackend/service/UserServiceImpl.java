package com.hsenidlanka.attendancemarkerbackend.service;

import com.hsenidlanka.attendancemarkerbackend.dto.request.UserRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.response.UserResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;
import com.hsenidlanka.attendancemarkerbackend.model.ERole;
import com.hsenidlanka.attendancemarkerbackend.model.User;
import com.hsenidlanka.attendancemarkerbackend.repository.UserRepository;
import com.hsenidlanka.attendancemarkerbackend.utils.exception.HandleException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public List<UserResponse> getAllUsers() {
        try {
            logger.info("Inside getAllUsers() function...");
            List<UserResponse> userResponseList = new ArrayList<>();
            List<User> userList = userRepository.findAll();
            logger.info(userList.toString());
            if (userList.isEmpty()) {
                logger.warn("No users available!");
                throw new HandleException("Exits without displaying data");
            } else {
                userList.forEach(user -> {
                            user.getRoles().stream().filter(role -> {
                                if (role.getName().name().equals("ROLE_USER")) {
                                    logger.info("Users are available..." + userList);
                                    UserResponse userResponse = new UserResponse();
                                    userResponse.setId(user.getId());
                                    userResponse.setUsername(user.getUsername());
                                    userResponse.setEmail(user.getEmail());
                                    userResponse.setCompany(user.getCompany());
                                    ;
                                    userResponseList.add(userResponse);
                                    return true;
                                } else {
                                    return false;
                                }
                            }).collect(Collectors.toList());
                        }
                );
                return userResponseList;
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    public UserResponse getUserById(String id) {
        try {
            logger.info("Inside getUserById() function...");
            Optional<User> userObj = userRepository.findById(id);
            if (userObj.isPresent()) {
                logger.info("A user available with this ID!" + userObj);
                User user = userObj.get();
                return modelMapper.map(user, UserResponse.class);
            } else {
                logger.warn("No user available with this ID!");
                throw new HandleException("Exits without displaying data");
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    public UserRequest updateUserById(String id, UserRequest userRequest) {
        try {
            logger.info("Inside updateUserById() function...");
            Optional<User> userObj = userRepository.findById(id);
            if (userObj.isPresent()) {
                logger.info("A user available with this ID!" + userObj);
                User user = userObj.get();
                user.setUsername(userRequest.getUsername());
                user.setEmail(userRequest.getEmail());
                user.setCompany(userRequest.getCompany());
                userRepository.save(modelMapper.map(user, User.class));
                logger.info("User updated...");
                return userRequest;
            } else {
                logger.warn("No user available with this ID!");
                throw new HandleException("Exits without updating data");
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    public MessageResponse deleteUserById(String id) {
        try {
            logger.info("Inside deleteUserById() function...");
            Optional<User> userObj = userRepository.findById(id);
            if (userObj.isPresent()) {
                logger.warn("A user available with this ID!" + userObj);
                userRepository.deleteById(id);
                return new MessageResponse("User deleted successfully!");
            } else {
                logger.warn("No user available with this ID!");
                throw new HandleException("Exits without deleting data");
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

    public List<UserResponse> getUsersByCompanyId(String id) {
        try {
            Optional<User> userList = userRepository.findById(id);
            if (userList.isEmpty()) {
                logger.warn("No users available in this company!");
                throw new HandleException("Exits without displaying data");
            } else {
                logger.info("Users are available in this company " + userList);
                return modelMapper.map(userList, new TypeToken<List<UserResponse>>() {
                }.getType());
            }
        } catch (Exception exception) {
            throw exception;
        }
    }

}
