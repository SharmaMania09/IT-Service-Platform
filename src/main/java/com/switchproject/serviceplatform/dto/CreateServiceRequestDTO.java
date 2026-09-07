package com.switchproject.serviceplatform.dto;

import com.switchproject.serviceplatform.enums.Priority;
import com.switchproject.serviceplatform.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateServiceRequestDTO 
{
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Priority priority;
    
    @NotNull
    private Status status;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }
}
