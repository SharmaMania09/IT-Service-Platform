package com.switchproject.serviceplatform.mapper;

import com.switchproject.serviceplatform.dto.ServiceRequestResponseDTO;
import com.switchproject.serviceplatform.entity.ServiceRequest;

public class ServiceRequestMapper 
{
    public ServiceRequestResponseDTO toResponseDTO(ServiceRequest request)
    {
        ServiceRequestResponseDTO reqRespDTO = new ServiceRequestResponseDTO(request.getId(),
            request.getTitle(), request.getDescription(), request.getPriority(),
            request.getStatus());
        
            return reqRespDTO;
    }
}
