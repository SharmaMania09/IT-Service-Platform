package com.switchproject.serviceplatform.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.switchproject.serviceplatform.dto.CreateServiceRequestDTO;
import com.switchproject.serviceplatform.dto.ServiceRequestResponseDTO;
import com.switchproject.serviceplatform.dto.UpdateServiceRequestDTO;
import com.switchproject.serviceplatform.dto.UpdateServiceRequestPatchDTO;
import com.switchproject.serviceplatform.entity.ServiceRequest;
import com.switchproject.serviceplatform.enums.Status;
import com.switchproject.serviceplatform.exception.ServiceRequestNotFoundException;
import com.switchproject.serviceplatform.mapper.ServiceRequestMapper;
import com.switchproject.serviceplatform.repository.ServiceRequestRepository;

@Service
public class ServiceRequestService
{
    @Autowired
    private ServiceRequestRepository repository;

    private ServiceRequestMapper mapper = new ServiceRequestMapper();

    private static final Logger logger = LoggerFactory.getLogger(ServiceRequestService.class);

    public ServiceRequestResponseDTO saveToRepo(CreateServiceRequestDTO request)
    {
        logger.info("Creating Service Request: {}", request.getTitle());
        
        ServiceRequest serviceRequest = new ServiceRequest(request.getTitle(), request.getDescription(), request.getPriority(), request.getStatus());
        
        serviceRequest.setCreatedAt(LocalDateTime.now());
        serviceRequest.setUpdatedAt(LocalDateTime.now());

        ServiceRequest serviceRequestObj = repository.save(serviceRequest);

        logger.info("Service request created successfully with ID: {}", serviceRequestObj.getId());

        ServiceRequestResponseDTO reqRespDTO = mapper.toResponseDTO(serviceRequestObj);

        return reqRespDTO;
    }

    @CacheEvict(
                value = "serviceRequestLists", 
                key = "#statusString + '_' + #pageable.pageNumber + '_' + #pageable.pageSize + '_' + #pageable.sort")
    public Page<ServiceRequestResponseDTO> getAllServiceRequestsData(String statusString, Pageable pageable)
    {
        Page<ServiceRequest> responseObj;
        
        if(statusString == null)
        {
            responseObj = repository.findAll(pageable);
        }
        else
        {
            Status status = Status.valueOf(statusString.toUpperCase());
            
            responseObj = repository.findAllByStatus(status, pageable);
        }

        return responseObj.map(mapper::toResponseDTO);
    }

    @Cacheable(value = "serviceRequests", key = "#request_id")
    public ServiceRequestResponseDTO getServiceRequestById(Long request_id)
    {
        logger.debug("Fetching service requests with ID: {}", request_id);
        
        Optional<ServiceRequest> requestObj = repository.findById(request_id);

        if(requestObj.isPresent())
        {
            ServiceRequest serviceRequestObj = requestObj.get();
            
            ServiceRequestResponseDTO reqRespDTO = mapper.toResponseDTO(serviceRequestObj);

            return reqRespDTO;
        }
        else
        {
            throw new ServiceRequestNotFoundException("Service request not found with ID: " + request_id);
        }
    }

    @CacheEvict(value = "serviceRequests", key = "#request_id")
    public void deleteServiceRequestById(Long request_id)
    {
        Optional<ServiceRequest> req = repository.findById(request_id);

        if(req.isPresent())
        {
            repository.deleteById(request_id);
        }
        else
        {
            throw new ServiceRequestNotFoundException("Service request not found with ID: " + request_id + " to delete !!!");
        }
    }

    @Caching
    (
        put = {
            @CachePut(
                value = "ServiceRequests",
                key = "#request_id"
            )
        },
        evict = {
            @CacheEvict(
                value = "serviceRequesLists",
                allEntries = true
            )
        }
    )
    public ServiceRequestResponseDTO updateById(Long request_id, UpdateServiceRequestDTO requestBody)
    {
        Optional<ServiceRequest> request = repository.findById(request_id);
        //
        if(request.isPresent())
        {
            ServiceRequest body = request.get();

            body.setDescription(requestBody.getDescription());
            body.setPriority(requestBody.getPriority());
            body.setStatus(requestBody.getStatus());
            body.setTitle(requestBody.getTitle());

            body.setUpdatedAt(LocalDateTime.now());

            repository.save(body);

            return mapper.toResponseDTO(body);
        }
        else
        {
            throw new ServiceRequestNotFoundException("Service request not found with ID: " + request_id + " to update !!!");
        }
    }

    @CachePut(value = "serviceRequests", key = "#request_id")
    public ServiceRequestResponseDTO updateRequiredField(Long request_id, UpdateServiceRequestPatchDTO requestBody)
    {
        Optional<ServiceRequest> request = repository.findById(request_id);
        //
        if(request.isPresent())
        {
            ServiceRequest body = request.get();

            if (requestBody.getDescription() != null) body.setDescription(requestBody.getDescription());
            if (requestBody.getPriority() != null) body.setPriority(requestBody.getPriority());
            if (requestBody.getStatus() != null) body.setStatus(requestBody.getStatus());
            if (requestBody.getTitle() != null) body.setTitle(requestBody.getTitle());

            body.setUpdatedAt(LocalDateTime.now());

            repository.save(body);

            return mapper.toResponseDTO(body);
        }
        else
        {
            throw new ServiceRequestNotFoundException("Service request not found with ID: " + request_id + " to update !!!");
        }
    }

    @Transactional
    public void testTransactionRollback(Long requestId)
    {
        ServiceRequest request = repository.findById(requestId)
                .orElseThrow(() ->
                        new ServiceRequestNotFoundException(
                                "Service request not found with ID: " + requestId));

        request.setTitle("THIS SHOULD ROLLBACK");

        repository.save(request);

        throw new RuntimeException("Testing transaction rollback");
    }

    @Transactional
    public void testTransactionCommit(Long requestId, String newTitle)
    {
        ServiceRequest request = repository.findById(requestId)
                .orElseThrow(() ->
                        new ServiceRequestNotFoundException(
                                "Service request not found with ID: " + requestId));

        request.setTitle(newTitle);

        // repository.save(request);

        // No exception
    }
}
