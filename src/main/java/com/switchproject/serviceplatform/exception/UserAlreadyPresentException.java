package com.switchproject.serviceplatform.exception;

public class UserAlreadyPresentException extends RuntimeException
{
    public UserAlreadyPresentException(String message)
    {
        super(message);
    }
}