package com.switchproject.serviceplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.switchproject.serviceplatform.dto.CreateServiceRequestDTO;
import com.switchproject.serviceplatform.dto.ServiceRequestResponseDTO;
import com.switchproject.serviceplatform.dto.UpdateServiceRequestDTO;
import com.switchproject.serviceplatform.dto.UpdateServiceRequestPatchDTO;
import com.switchproject.serviceplatform.service.ServiceRequestService;

import jakarta.validation.Valid;



@RestController
public class ServicePlatformController
{
    @Autowired
    private ServiceRequestService serviceRequestService;
    
    @GetMapping("/")
    public String getDefaultMapping()
    {
        return "Hello, from SpringBoot";
    }

    @PostMapping("/service-request")
    public ResponseEntity<ServiceRequestResponseDTO> saveRequest(@Valid @RequestBody CreateServiceRequestDTO request)
    {
        ServiceRequestResponseDTO savedRequest = serviceRequestService.saveToRepo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRequest);
    }

    @GetMapping("/service-requests")
    public ResponseEntity<Page<ServiceRequestResponseDTO>> getServiceRequests(@RequestParam(value = "status", required = false) String statusString, Pageable pageable)
    {
        Page<ServiceRequestResponseDTO> fetchedServiceRequests = serviceRequestService.getAllServiceRequestsData(statusString, pageable);
        return ResponseEntity.ok(fetchedServiceRequests);
    }

    @GetMapping("/service-requests/{request_id}")
    public ResponseEntity<ServiceRequestResponseDTO> getServiceRequestById(@PathVariable Long request_id)
    {
        ServiceRequestResponseDTO fetchedRequest = serviceRequestService.getServiceRequestById(request_id);
        return ResponseEntity.ok(fetchedRequest);
    }

    @DeleteMapping("/service-requests/{request_id}")
    public ResponseEntity<Void> deleteServiceRequestById(@PathVariable Long request_id)
    {
        serviceRequestService.deleteServiceRequestById(request_id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/service-request/{requestId}")
    public ResponseEntity<ServiceRequestResponseDTO> updateById(@PathVariable Long requestId, @Valid @RequestBody UpdateServiceRequestDTO requestBody) 
    {
        ServiceRequestResponseDTO updatedRequest = serviceRequestService.updateById(requestId, requestBody);

        return ResponseEntity.status(HttpStatus.OK).body(updatedRequest);
    }
    
    @PatchMapping("/service-request/{requestId}")
    public ResponseEntity<ServiceRequestResponseDTO> updateRequiredField(@PathVariable Long requestId, @Valid @RequestBody UpdateServiceRequestPatchDTO requestBody) 
    {
        ServiceRequestResponseDTO updatedRequest = serviceRequestService.updateRequiredField(requestId, requestBody);

        return ResponseEntity.status(HttpStatus.OK).body(updatedRequest);
    }

    @GetMapping("/test-rollback/{requestId}")
    public ResponseEntity<String> testRollback(@PathVariable Long requestId)
    {
        serviceRequestService.testTransactionRollback(requestId);

        return ResponseEntity.ok("Transaction completed");
    }

    @PostMapping("/test-commit/{requestId}")
    public ResponseEntity<String> testCommit(@PathVariable Long requestId, @RequestParam String newTitle)
    {
        serviceRequestService.testTransactionCommit(requestId, newTitle);

        return ResponseEntity.ok("Transaction committed successfully");
    }
}
