package com.switchproject.serviceplatform.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateUserDTO
{
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}