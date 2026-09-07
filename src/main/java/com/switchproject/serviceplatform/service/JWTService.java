package com.switchproject.serviceplatform.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService
{
    private final String SECRET_KEY = "my-super-secret-key-for-switch-platform";

    private SecretKey getSigningKey()
    {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
    
    public String generateToken(UserDetails userDetails)
    {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        
        return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                    .signWith(key)
                    .compact();
    }

    public String extractUsername(String token)
    {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails)
    {
        String username = extractUsername(token);

        return username.equals(userDetails.getUsername());
    }
}