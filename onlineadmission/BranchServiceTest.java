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

import com.project.onlineadmission.dtos.BranchDTO;
import com.project.onlineadmission.entities.Branch;
import com.project.onlineadmission.repositories.BranchRepository;
import com.project.onlineadmission.service.BranchService;

public class BranchServiceTest {

   
    @InjectMocks
    private BranchService branchService;
    
    @Mock
    private BranchRepository branchRepository;
    
    @Mock
    private ModelMapper modelMapper;
    

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddBranch_ValidBranch() {
        // Given
        BranchDTO inputBranchDTO = new BranchDTO(1, "Test Branch", "Test Description");
        Branch inputBranchEntity = new Branch();
        inputBranchEntity.setBranchId(1);
        inputBranchEntity.setBranchName("Test Branch");
        inputBranchEntity.setBranchDescription("Test Description");
        when(modelMapper.map(inputBranchDTO, Branch.class)).thenReturn(inputBranchEntity);
        when(branchRepository.save(inputBranchEntity)).thenReturn(inputBranchEntity);
        when(modelMapper.map(inputBranchEntity, BranchDTO.class)).thenReturn(inputBranchDTO);

        // When
        BranchDTO result = branchService.addBranch(inputBranchDTO);

        // Then
        assertEquals(inputBranchDTO, result);
    }

    @Test
    public void testDeleteBranch_ValidBranchId() {
        // Given
        int branchId = 1;
        doNothing().when(branchRepository).deleteById(branchId);

        // When
        int result = branchService.deleteById(branchId);

        // Then
        assertEquals(1, result);
    }


    @Test
    public void testUpdateBranch_ValidBranchId() {
        // Given
        Integer branchId = 1;
        BranchDTO branchDTO = new BranchDTO(1, "Test Branch", "Test Description");
        Branch existingBranch = new Branch();
        existingBranch.setBranchId(1);
        existingBranch.setBranchName("Existing Branch");
        existingBranch.setBranchDescription("Existing Description");
        
        // Mock behavior of repository
        when(branchRepository.findById(branchId)).thenReturn(Optional.of(existingBranch));
        when(branchRepository.save(existingBranch)).thenReturn(existingBranch);
        
        // Mock behavior of ModelMapper
        when(modelMapper.map(branchDTO, Branch.class)).thenReturn(existingBranch);
        when(modelMapper.map(existingBranch, BranchDTO.class)).thenReturn(branchDTO);

        // When
        BranchDTO result = branchService.updateBranch(branchId, branchDTO);

        // Then
        assertNotNull(result);
        assertEquals(branchDTO, result);
    }
    
    
    @Test
    public void testGetBranchById_ValidBranchId() {
        // Given
        int branchId = 1;
        BranchDTO expectedBranchDTO = new BranchDTO(1, "Test Branch", "Test Description");
        Branch expectedBranchEntity = new Branch();
        expectedBranchEntity.setBranchId(1);
        expectedBranchEntity.setBranchName("Test Branch");
        expectedBranchEntity.setBranchDescription("Test Description");
        when(branchRepository.findById(branchId)).thenReturn(Optional.of(expectedBranchEntity));
        when(modelMapper.map(expectedBranchEntity, BranchDTO.class)).thenReturn(expectedBranchDTO);

        // When
        BranchDTO result = branchService.getBranchById(branchId);

        // Then
        assertEquals(expectedBranchDTO, result);
    }

}
