package com.hsenidlanka.attendancemarkerbackend.utils.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.hsenidlanka.attendancemarkerbackend.service.UserDetailsImpl;
import io.jsonwebtoken.*;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${attendance.marker.backend.jwtSecret}")
    private String jwtSecret;

    @Value("${attendance.marker.backend.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Generate JWT token
     **/
    public String generateJwtToken(Authentication authentication) {
        logger.info("JwtUtils - generateJwtToken()");
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Get username from JWT token
     **/
    public String getUserNameFromJwtToken(String token) {
        logger.info("JwtUtils - getUserNameFromJwtToken()");
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Validate JWT token
     **/
    public boolean validateJwtToken(String authToken) {
        try {
            logger.info("JwtUtils - validateJwtToken()");
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
