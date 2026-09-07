package com.switchproject.serviceplatform.exception;

public class ServiceRequestNotFoundException extends RuntimeException
{
    public ServiceRequestNotFoundException(String message)
    {
        super(message);
    }
}
