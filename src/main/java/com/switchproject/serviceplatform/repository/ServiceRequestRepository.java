package com.switchproject.serviceplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.switchproject.serviceplatform.entity.ServiceRequest;
import com.switchproject.serviceplatform.enums.Status;


public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long>
{
    Page<ServiceRequest> findAllByStatus(Status status, Pageable pageable);
}
