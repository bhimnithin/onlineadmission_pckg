package com.project.onlineadmission;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 
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
import com.project.onlineadmission.dtos.ProgramDTO;
import com.project.onlineadmission.entities.Program;
import com.project.onlineadmission.repositories.ProgramRespository;
import com.project.onlineadmission.service.ProgramService;
 
public class ProgramServiceTest {
 
    @InjectMocks
    private ProgramService programService;
 
    @Mock
    private ProgramRespository programRepository;
 
    @Mock
    private ModelMapper modelMapper;
 
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
 
    @Test
    public void testAddProgram() {
        ProgramDTO programDTO = new ProgramDTO();
        programDTO.setProgramId(1);
        Program program = new Program();
        program.setProgramId(1);
 
        when(modelMapper.map(programDTO, Program.class)).thenReturn(program);
        when(programRepository.save(program)).thenReturn(program);
        when(modelMapper.map(program, ProgramDTO.class)).thenReturn(programDTO);
 
        ProgramDTO result = programService.addProgram(programDTO);
 
        assertEquals(programDTO, result);
    }
 
    @Test
    public void testGetProgramById_ValidId() {
        Program program = new Program();
        program.setProgramId(1);
        ProgramDTO programDTO = new ProgramDTO();
        programDTO.setProgramId(1);
 
        when(programRepository.findById(1)).thenReturn(Optional.of(program));
        when(modelMapper.map(program, ProgramDTO.class)).thenReturn(programDTO);
 
        ProgramDTO result = programService.getProgramById(1);
 
        assertEquals(programDTO, result);
    }
 
    @Test
    public void testGetProgramById_InvalidId() {
        when(programRepository.findById(1)).thenReturn(Optional.empty());
 
        ProgramDTO result = programService.getProgramById(1);
 
        assertEquals(null, result);
    }
    @Test
    public void testDeleteById_ValidId() {
        doNothing().when(programRepository).deleteById(1);
        int result = programService.deleteById(1);
        assertEquals(1, result);
        verify(programRepository, times(1)).deleteById(1);
    }
    
    @Test
    public void testUpdateProgram_ValidProgram() {
        ProgramDTO programDTO = new ProgramDTO();
        programDTO.setProgramId(1);
        Program program = new Program();
        program.setProgramId(1);
 
        when(programRepository.findById(1)).thenReturn(Optional.of(program));
        when(modelMapper.map(programDTO, Program.class)).thenReturn(program);
        when(programRepository.save(program)).thenReturn(program);
        when(modelMapper.map(program, ProgramDTO.class)).thenReturn(programDTO);
 
        ProgramDTO result = programService.updateProgram(programDTO);
 
        assertEquals(programDTO, result);
    }
    
    @Test
    public void testUpdateProgram_InvalidProgram() {
        ProgramDTO programDTO = new ProgramDTO();
        programDTO.setProgramId(1);
 
        when(programRepository.findById(1)).thenReturn(Optional.empty());
 
        ProgramDTO result = programService.updateProgram(programDTO);
 
        assertEquals(null, result);
    }
    @Test
    public void testGetAllPrograms() {
        List<Program> programs = new ArrayList<>();
        Program program1 = new Program();
        program1.setProgramId(1);
        programs.add(program1);
 
        ProgramDTO programDTO1 = new ProgramDTO();
        programDTO1.setProgramId(1);
        List<ProgramDTO> programDTOs = new ArrayList<>();
        programDTOs.add(programDTO1);
 
        when(programRepository.findAll()).thenReturn(programs);
        when(modelMapper.map(programs, new TypeToken<List<ProgramDTO>>() {}.getType())).thenReturn(programDTOs);
 
        List<ProgramDTO> result = programService.getAllPrograms();
 
        assertEquals(programDTOs, result);
    }
}
