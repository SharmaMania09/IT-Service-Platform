package com.switchproject.serviceplatform.controller;

import org.springframework.web.bind.annotation.RestController;

import com.switchproject.serviceplatform.dto.LoginRequestDTO;
import com.switchproject.serviceplatform.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AuthController
{
    @Autowired
    private AuthService authService;
    
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginCredentials)
    {
        String token = authService.login(loginCredentials);

        return ResponseEntity.ok(token);
    }
}