package com.switchproject.serviceplatform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.switchproject.serviceplatform.dto.CreateServiceRequestDTO;
import com.switchproject.serviceplatform.dto.ServiceRequestResponseDTO;
import com.switchproject.serviceplatform.entity.ServiceRequest;
import com.switchproject.serviceplatform.enums.Priority;
import com.switchproject.serviceplatform.enums.Status;
import com.switchproject.serviceplatform.exception.ServiceRequestNotFoundException;
import com.switchproject.serviceplatform.repository.ServiceRequestRepository;

@ExtendWith(MockitoExtension.class)
public class ServiceRequestServiceTest
{
    @Mock
    private ServiceRequestRepository repository;

    @InjectMocks
    private ServiceRequestService service;

    @Test
    void shouldFindServiceRequestById()
    {
        ServiceRequest req = new ServiceRequest("Laptop is not working", "Laptop is not starting", Priority.HIGH, Status.OPEN);

        req.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(req));

        ServiceRequestResponseDTO result = service.getServiceRequestById(1L);

        assertEquals(1L, result.getRequestId());
        assertEquals("Laptop is not working", result.getTitle());
        assertEquals("Laptop is not starting", result.getDescription());
        assertEquals(Priority.HIGH, result.getPriority());
        assertEquals(Status.OPEN, result.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenServiceRequestNotFound()
    {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ServiceRequestNotFoundException.class, () -> service.getServiceRequestById(99L));
    }

    @Test
    void shouldCreateServiceRequest()
    {
        CreateServiceRequestDTO request = mock(CreateServiceRequestDTO.class);

        when(request.getTitle()).thenReturn("Laptop issue");
        when(request.getDescription()).thenReturn("Laptop is not starting");
        when(request.getPriority()).thenReturn(Priority.HIGH);
        when(request.getStatus()).thenReturn(Status.OPEN);

        ServiceRequest savedReq = new ServiceRequest("Laptop issue", "Laptop is not starting", Priority.HIGH, Status.OPEN);

        savedReq.setId(2L);

        when(repository.save(any(ServiceRequest.class)))
            .thenReturn(savedReq);

        ServiceRequestResponseDTO result = service.saveToRepo(request);

        assertEquals(2L, result.getRequestId());
        assertEquals("Laptop issue", result.getTitle());
        assertEquals("Laptop is not starting", result.getDescription());
        assertEquals(Priority.HIGH, result.getPriority());
        assertEquals(Status.OPEN, result.getStatus());

        verify(repository).save(any(ServiceRequest.class));
    }
}