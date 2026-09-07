package com.switchproject.serviceplatform.dto;

import com.switchproject.serviceplatform.enums.Priority;
import com.switchproject.serviceplatform.enums.Status;

import jakarta.validation.constraints.Size;

public class UpdateServiceRequestPatchDTO 
{
    @Size(min=1)
    private String title;

    @Size(min=1)
    private String description;

    private Priority priority;
    
    private Status status;

    public UpdateServiceRequestPatchDTO() {
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}