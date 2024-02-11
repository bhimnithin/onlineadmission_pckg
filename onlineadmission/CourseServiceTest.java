package com.project.onlineadmission;

import com.google.gson.reflect.TypeToken;
import com.project.onlineadmission.dtos.CourseDTO;
import com.project.onlineadmission.entities.Course;
import com.project.onlineadmission.repositories.CourseRepository;
import com.project.onlineadmission.service.CourseService;
 
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
 
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
@SpringBootTest
public class CourseServiceTest {
 
    @InjectMocks
    private CourseService courseService;
 
    @Mock
    private CourseRepository courseRepository;
 
    @Mock
    private ModelMapper modelMapper;
 
    @Test
    public void testAddCourse() {
        CourseDTO courseDTO = new CourseDTO();
        Course course = new Course();
        when(modelMapper.map(courseDTO, Course.class)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(modelMapper.map(course, CourseDTO.class)).thenReturn(courseDTO);
 
        CourseDTO result = courseService.addCourse(courseDTO);
 
        assertEquals(courseDTO, result);
    }
 
    @Test
    public void testDeleteById() {
        doNothing().when(courseRepository).deleteById(1);
        assertEquals(1, courseService.deleteById(1));
    }
 
    @Test
    public void testUpdateCourse_Valid() {
        CourseDTO courseDTO = new CourseDTO();
        Course course = new Course();
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(modelMapper.map(courseDTO, Course.class)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(modelMapper.map(course, CourseDTO.class)).thenReturn(courseDTO);
 
        CourseDTO result = courseService.updateCourse(1, courseDTO);
 
        assertEquals(courseDTO, result);
    }
 
    @Test
    public void testUpdateCourse_Invalid() {
        CourseDTO courseDTO = new CourseDTO();
        when(courseRepository.findById(1)).thenReturn(Optional.empty());
 
        CourseDTO result = courseService.updateCourse(1, courseDTO);
 
        assertNull(result);
    }
 
    @Test
    public void testGetCourseById_Valid() {
        Course course = new Course();
        CourseDTO courseDTO = new CourseDTO();
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(modelMapper.map(course, CourseDTO.class)).thenReturn(courseDTO);
 
        CourseDTO result = courseService.getCourseById(1);
 
        assertEquals(courseDTO, result);
    }
 
    @Test
    public void testGetCourseById_Invalid() {
        when(courseRepository.findById(1)).thenReturn(Optional.empty());
 
        CourseDTO result = courseService.getCourseById(1);
 
        assertNull(result);
    }
 
    @Test
    public void testGetAllCourses() {
        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> courses = Arrays.asList(course1, course2);
        CourseDTO courseDTO1 = new CourseDTO();
        CourseDTO courseDTO2 = new CourseDTO();
        List<CourseDTO> courseDTOs = Arrays.asList(courseDTO1, courseDTO2);
        when(courseRepository.findAll()).thenReturn(courses);
        when(modelMapper.map(courses, new TypeToken<List<CourseDTO>>() {}.getType())).thenReturn(courseDTOs);
 
        List<CourseDTO> result = courseService.getAllCourses();
 
        assertEquals(courseDTOs, result);
    }
}
