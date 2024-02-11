package com.project.onlineadmission;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.google.gson.reflect.TypeToken;
import com.project.onlineadmission.dtos.ApplicationDTO;
import com.project.onlineadmission.entities.Application;
import com.project.onlineadmission.repositories.ApplicationRepository;
import com.project.onlineadmission.service.ApplicationService;

public class ApplicationServiceTest {

	//mock its like create a virtual object of application repository saying without going to that method refer this method
    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ApplicationService applicationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddApplication_ValidApplication() {
        // Given
        ApplicationDTO inputDTO = new ApplicationDTO("John Doe", "1990-01-01", "Bachelor's", 80.0, "Career Growth", "john.doe@example.com", "Pending");
        Application applicationEntity = new Application();
        when(modelMapper.map(inputDTO, Application.class)).thenReturn(applicationEntity);
        when(applicationRepository.save(applicationEntity)).thenReturn(applicationEntity);
        ApplicationDTO expectedDTO = new ApplicationDTO("John Doe", "1990-01-01", "Bachelor's", 80.0, "Career Growth", "john.doe@example.com", "Pending");
        when(modelMapper.map(applicationEntity, ApplicationDTO.class)).thenReturn(expectedDTO);

        // When
        ApplicationDTO result = applicationService.addApplication(inputDTO);

        // Then
        assertNotNull(result);
        assertEquals(expectedDTO, result);
    }

    @Test
    public void testUpdateApplication_ValidApplication() {
        // Given
        ApplicationDTO inputDTO = new ApplicationDTO("John Doe", "1990-01-01", "Bachelor's", 80.0, "Career Growth", "john.doe@example.com", "Pending");
        Application existingApplication = new Application();
        existingApplication.setApplicationId(1);
        when(applicationRepository.findById(inputDTO.getApplicationId())).thenReturn(Optional.of(existingApplication));
        when(applicationRepository.save(existingApplication)).thenReturn(existingApplication);
        ApplicationDTO expectedDTO = new ApplicationDTO("John Doe", "1990-01-01", "Bachelor's", 80.0, "Career Growth", "john.doe@example.com", "Pending");
        when(modelMapper.map(existingApplication, ApplicationDTO.class)).thenReturn(expectedDTO);

        // When
        ApplicationDTO result = applicationService.updateApplication(inputDTO);

        // Then
        assertNotNull(result);
        assertEquals(expectedDTO, result);
    }

    @Test
    public void testGetApplicationById_ValidId() {
        // Given
        Integer applicationId = 1;
        Application existingApplication = new Application();
        existingApplication.setApplicationId(applicationId);
        when(applicationRepository.findById(applicationId)).thenReturn(Optional.of(existingApplication));
        ApplicationDTO expectedDTO = new ApplicationDTO();
        expectedDTO.setApplicationId(applicationId);
        when(modelMapper.map(existingApplication, ApplicationDTO.class)).thenReturn(expectedDTO);

        // When
        ApplicationDTO result = applicationService.getApplicationById(applicationId);

        // Then
        assertNotNull(result);
        assertEquals(expectedDTO, result);
    }

    @Test
    public void testGetAllApplications() {
        // Given
        List<Application> applications = new ArrayList<>();
        applications.add(new Application());
        when(applicationRepository.findAll()).thenReturn(applications);
        List<ApplicationDTO> expectedDTOs = new ArrayList<>();
        expectedDTOs.add(new ApplicationDTO());
        when(modelMapper.map(applications, new TypeToken<List<ApplicationDTO>>() {}.getType())).thenReturn(expectedDTOs);

        // When
        List<ApplicationDTO> result = applicationService.getAllApplications();

        // Then
        assertNotNull(result);
        assertEquals(expectedDTOs.size(), result.size());
    }
}

