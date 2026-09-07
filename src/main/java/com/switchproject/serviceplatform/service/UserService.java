package com.switchproject.serviceplatform.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.switchproject.serviceplatform.dto.CreateUserDTO;
import com.switchproject.serviceplatform.entity.User;
import com.switchproject.serviceplatform.enums.Role;
import com.switchproject.serviceplatform.exception.UserAlreadyPresentException;
import com.switchproject.serviceplatform.repository.UserRepository;

@Service
public class UserService 
{
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addUserToRepo(CreateUserDTO userDetails)
    {
        String userName = userDetails.getUsername();

        Optional<User> user = userRepo.findByUsername(userName);

        if(!user.isPresent())
        {
            User newUser = new User();
            
            newUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            newUser.setRole(Role.USER);
            newUser.setUsername(userDetails.getUsername());
            
            userRepo.save(newUser);
        }
        else
        {
            throw new UserAlreadyPresentException("User with username " + userName + " is already present !!!");
        }
    }
}