package com.switchproject.serviceplatform.dto;

import com.switchproject.serviceplatform.enums.Priority;
import com.switchproject.serviceplatform.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ServiceRequestResponseDTO
{
    private Long requestId;
    
    private String title;

    private String description;

    private Priority priority;
    
    private Status status;

    public ServiceRequestResponseDTO(@NotNull Long requestId, @NotBlank String title, @NotBlank String description,
            @NotNull Priority priority, @NotNull Status status) {
        this.requestId = requestId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getRequestId() {
        return requestId;
    }

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
