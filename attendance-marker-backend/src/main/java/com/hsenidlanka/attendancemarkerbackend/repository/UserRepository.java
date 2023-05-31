package com.hsenidlanka.attendancemarkerbackend.repository;

import com.hsenidlanka.attendancemarkerbackend.dto.request.ResetPasswordRequest;
import com.hsenidlanka.attendancemarkerbackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findById(String id);

    Optional<User> findByUsername(String userName);

     boolean existsByEmail(String userName);

     boolean existsByUsername(String email);

    Optional<User> findByUsernameAndEmail(String username, String email);

}
