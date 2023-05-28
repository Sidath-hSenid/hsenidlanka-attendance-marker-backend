package com.hsenidlanka.attendancemarkerbackend.repository;

import java.util.Optional;

import com.hsenidlanka.attendancemarkerbackend.model.RefreshToken;
import com.hsenidlanka.attendancemarkerbackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);

    int deleteByUser(User user);
}
