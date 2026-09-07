package com.switchproject.serviceplatform.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.switchproject.serviceplatform.dto.LoginRequestDTO;

@Service
public class AuthService
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    public String login(LoginRequestDTO loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return jwtService.generateToken(userDetails);
    }
}