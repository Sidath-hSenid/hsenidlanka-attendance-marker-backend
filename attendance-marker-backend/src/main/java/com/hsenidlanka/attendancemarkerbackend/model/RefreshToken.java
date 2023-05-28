package com.hsenidlanka.attendancemarkerbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "refresh_token")
public class RefreshToken {
    @Id
    private String id;

    @DBRef
    private User user;

    private String token;

    private Instant expiryDate;

    // getters and setters
}
