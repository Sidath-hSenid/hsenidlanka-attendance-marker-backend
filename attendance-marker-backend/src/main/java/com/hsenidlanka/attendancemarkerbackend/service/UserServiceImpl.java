package com.hsenidlanka.attendancemarkerbackend.service;

import com.hsenidlanka.attendancemarkerbackend.dto.request.UserRequest;
import com.hsenidlanka.attendancemarkerbackend.dto.response.UserResponse;
import com.hsenidlanka.attendancemarkerbackend.dto.response.MessageResponse;
import com.hsenidlanka.attendancemarkerbackend.model.User;
import com.hsenidlanka.attendancemarkerbackend.repository.UserRepository;
import com.hsenidlanka.attendancemarkerbackend.utils.exception.HandleException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public List<UserResponse> getAllUsers(){
        try{
            logger.info("Inside getAllUsers() function...");
            List<User> userList = userRepository.findAll();
            if(userList.isEmpty()){
                logger.warn("No users available!");
                throw new HandleException("Exits without displaying data");
            }
            else{
                logger.info("Users are available..." + userList);
                return modelMapper.map(userList, new TypeToken<List<UserResponse>>(){}.getType());
            }
        }catch(Exception exception){
            throw exception;
        }
    }

    public UserResponse getUserById(String id){
        try{
            logger.info("Inside getUserById() function...");
            Optional<User> userObj = userRepository.findById(id);
            if(userObj.isPresent()){
                logger.info("A user available with this ID!" + userObj);
                User user = userObj.get();
                return modelMapper.map(user, UserResponse.class);
            }else{
                logger.warn("No user available with this ID!");
                throw new HandleException("Exits without displaying data");
            }
        }catch(Exception exception){
            throw exception;
        }
    }

    public UserRequest updateUserById(String id, UserRequest userRequest){
        try{
            logger.info("Inside updateUserById() function...");
            Optional<User> userObj = userRepository.findById(id);
            if(userObj.isPresent()){
                logger.info("A user available with this ID!" + userObj);
                User user = userObj.get();
                user.setUsername(userRequest.getUsername());
                user.setEmail(userRequest.getEmail());
                user.setCompany(userRequest.getCompany());
                userRepository.save(modelMapper.map(user, User.class));
                logger.info("User updated...");
                return userRequest;
            }else{
                logger.warn("No user available with this ID!");
                throw new HandleException("Exits without updating data");
            }
        }catch(Exception exception){
            throw exception;
        }
    }

    public MessageResponse deleteUserById(String id){
        try {
            logger.info("Inside deleteUserById() function...");
            Optional<User> userObj = userRepository.findById(id);
            if(userObj.isPresent()){
                logger.warn("A user available with this ID!" + userObj);
                userRepository.deleteById(id);
                return new MessageResponse("User deleted successfully!");
            }else{
                logger.warn("No user available with this ID!");
                throw new HandleException("Exits without deleting data");
            }
        }catch(Exception exception){
            throw exception;
        }
    }

}
