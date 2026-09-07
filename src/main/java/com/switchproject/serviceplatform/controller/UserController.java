package com.switchproject.serviceplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.switchproject.serviceplatform.dto.CreateUserDTO;
import com.switchproject.serviceplatform.service.UserService;

import jakarta.validation.Valid;


@RestController
public class UserController
{
    @Autowired
    private UserService userService;
    
    @PostMapping("/user/add")
    public void addUserToRepo(@Valid @RequestBody CreateUserDTO userDetails)
    {
        userService.addUserToRepo(userDetails);
    }
}