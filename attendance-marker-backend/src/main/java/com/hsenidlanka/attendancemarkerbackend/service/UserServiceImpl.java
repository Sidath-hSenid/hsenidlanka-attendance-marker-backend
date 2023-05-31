package com.hsenidlanka.attendancemarkerbackend.service;

import com.hsenidlanka.attendancemarkerbackend.dto.request.*;
import com.hsenidlanka.attendancemarkerbackend.dto.response.*;
import com.hsenidlanka.attendancemarkerbackend.model.Attendance;
import com.hsenidlanka.attendancemarkerbackend.model.User;
import com.hsenidlanka.attendancemarkerbackend.repository.AttendanceRepository;
import com.hsenidlanka.attendancemarkerbackend.repository.UserRepository;
import com.hsenidlanka.attendancemarkerbackend.utils.exception.HandleException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Retrieve all users
     **/
    public GetUserResponseList getAllUsers() {
        try {
            logger.info("UserServiceImpl - getAllUsers()");
            List<UserResponse> userResponseList = new ArrayList<>();
            List<User> userList = userRepository.findAll();
            logger.info(userList.toString());
            if (userList.isEmpty()) {
                logger.error("UserServiceImpl - getAllUsers(No users available)");
                return new GetUserResponseList(userResponseList, 404);
            } else {
                userList.forEach(user -> {
                            user.getRoles().stream().filter(role -> {
                                if (role.getName().name().equals("ROLE_USER")) {
                                    logger.info("UserServiceImpl - getAllUsers(Users are available)");
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
                return new GetUserResponseList(userResponseList, 200);
            }
        } catch (Exception exception) {
            return new GetUserResponseList(null, 400);
        }
    }

    /**
     * Retrieve user by user ID
     **/
    public GetUserResponse getUserById(String id) {
        try {
            logger.info("UserServiceImpl - getUserById()");
            Optional<User> userObj = userRepository.findById(id);
            if (userObj.isPresent()) {
                logger.info("UserServiceImpl - getUserById(A user available with this ID)");
                User user = userObj.get();
                return new GetUserResponse(modelMapper.map(user, UserResponse.class), 200);
            } else {
                logger.error("UserServiceImpl - getUserById(No user available with this ID)");
                return new GetUserResponse(null, 404);
            }
        } catch (Exception exception) {
            return new GetUserResponse(null, 400);
        }
    }

    /**
     * Update user by user ID
     **/
    public PutUserRequest updateUserById(String id, UserRequest userRequest) {
        try {
            logger.info("UserServiceImpl - updateUserById()");
            Optional<User> userObj = userRepository.findById(id);
            if (userObj.isPresent()) {
                logger.info("UserServiceImpl - updateUserById(An user available with this ID)");
                User user = userObj.get();
                user.setUsername(userRequest.getUsername());
                user.setEmail(userRequest.getEmail());
                user.setCompany(userRequest.getCompany());
                userRepository.save(modelMapper.map(user, User.class));
                return new PutUserRequest(userRequest, 200);
            } else {
                logger.error("UserServiceImpl - updateUserById(No user available with this ID)");
                return new PutUserRequest(null, 404);
            }
        } catch (Exception exception) {
            return new PutUserRequest(null, 400);
        }
    }

    /**
     * Delete user by user ID
     **/
    public MessageResponse deleteUserById(String id) {
        try {
            logger.info("UserServiceImpl - deleteUserById()");
            Optional<User> userObj = userRepository.findById(id);
            List<UserResponse> userAttendanceResponseList = new ArrayList<>();
            List<Attendance> attendanceList = attendanceRepository.findAll();
            if (!attendanceList.isEmpty()) {
                logger.info("UserServiceImpl - deleteUserById(userResponseList not empty)");
                attendanceList.stream().filter(attendance -> {
                    System.out.println();
                    if (attendance.getUser().getId().equals(id)) {
                        logger.info("UserServiceImpl - deleteUserById(Users available with company ID)");
                        UserResponse userResponse = new UserResponse();
                        userResponse.setId(attendance.getId());
                        userAttendanceResponseList.add(userResponse);
                        return true;
                    } else {
                        return false;
                    }
                }).collect(Collectors.toList());
                if (userAttendanceResponseList.isEmpty()) {
                    if (userObj.isPresent()) {
                        userRepository.deleteById(id);
                        logger.info("UserServiceImpl - deleteUserById(User deleted successfully)");
                        return new MessageResponse("User Deleted Successfully!", 200);
                    } else {
                        logger.error("UserServiceImpl - deleteUserById(Unable to delete user)");
                        return new MessageResponse("Unable to delete user!", 400);
                    }
                } else {
                    logger.error("UserServiceImpl - deleteUserById(User has recorded attendances)");
                    return new MessageResponse("Unable to delete user since it has recorded attendances!", 400);
                }
            } else {
                if (userObj.isPresent()) {
                    userRepository.deleteById(id);
                    logger.info("UserServiceImpl - deleteUserById(User deleted successfully)");
                    return new MessageResponse("User Deleted Successfully!", 200);
                } else {
                    logger.error("UserServiceImpl - deleteUserById(Unable to delete user)");
                    return new MessageResponse("Unable to delete user!", 400);
                }
            }
        } catch (Exception exception) {
            return new MessageResponse("Something went wrong!", 400);
        }
    }

    /**
     * Retrieve user by company ID
     **/
    public GetUserResponseList getUsersByCompanyId(String id) {
        try {
            logger.info("UserServiceImpl - getUsersByCompanyId()");
            List<UserResponse> userResponseList = new ArrayList<>();
            List<User> userList = userRepository.findAll();
            List<UserResponse> userCompanyResponseList = new ArrayList<>();
            logger.info(userList.toString());
            if (userList.isEmpty()) {
                logger.error("UserServiceImpl - getUsersByCompanyId(No users available)");
                throw new HandleException("Exits without displaying data");
            } else {
                userList.forEach(user -> {
                            user.getRoles().stream().filter(role -> {
                                if (role.getName().name().equals("ROLE_USER")) {
                                    logger.info("UserServiceImpl - getUsersByCompanyId(Users are available)");
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
                if (!userResponseList.isEmpty()) {
                    logger.info("UserServiceImpl - getUsersByCompanyId(userResponseList not empty)");
                    userResponseList.stream().filter(user -> {
                        System.out.println();
                        if (user.getCompany().getId().equals(id)) {
                            logger.info("UserServiceImpl - getUsersByCompanyId(Users available with company ID)");
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
                    return new GetUserResponseList(userCompanyResponseList, 200);
                } else {
                    logger.error("UserServiceImpl - getUsersByCompanyId(No user available with this ID)");
                    return new GetUserResponseList(null, 404);
                }
            }

        } catch (Exception exception) {
            return new GetUserResponseList(null, 400);
        }
    }

    public MessageResponse resetPassword(String username, String email, ResetPasswordRequest resetPasswordRequest){
        try {
            logger.info("UserServiceImpl - resetPassword()");
            Optional<User> userObj = userRepository.findByUsernameAndEmail(username, email);
            if (userObj.isPresent()) {
                logger.info("UserServiceImpl - resetPassword(An user available with username and email)");
                userObj.get().setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
                userRepository.save(modelMapper.map(userObj, User.class));
                return new MessageResponse("Password reset successfully!", 200);
            } else {
                logger.error("UserServiceImpl - resetPassword(Unable find a user with given username and email)");
                return new MessageResponse("Unable find a user with given username and email!", 404);
            }
        } catch (Exception exception) {
            return new MessageResponse(null, 400);
        }
    }

}
