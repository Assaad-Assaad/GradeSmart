package be.ehb.gradesmart;

import be.ehb.gradesmart.model.Course;
import be.ehb.gradesmart.model.Student;
import be.ehb.gradesmart.repository.CourseRepository;
import be.ehb.gradesmart.service.CourseService;
import be.ehb.gradesmart.service.exeption.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
public class CourseServiceTests {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course;
    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student("John Doe", 20, true);
        course = new Course("Mathematics", "Dr. Smith", 5);
    }

    @Test
    public void testSaveCourse() {
        Mockito.when(courseRepository.save(course)).thenReturn(course);

        courseService.save(course);

        Mockito.verify(courseRepository, Mockito.times(1)).save(course);
    }

    @Test
    public void testAllStudentsAssignedToACourse() {
        course.getStudents().add(student);
        Mockito.when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));


        Map<String, Object> result = courseService.allStudentsAssignedToACourse(1);


        assertNotNull(result);
        assertEquals("Mathematics", result.get("courseName"));
        assertTrue(((List<?>) result.get("studentNames")).contains("John Doe"));
    }

    @Test
    public void testAllStudentsAssignedToACourseNotFound() {

        Mockito.when(courseRepository.findById(anyInt())).thenReturn(Optional.empty());


        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            courseService.allStudentsAssignedToACourse(1);
        });
        assertEquals("Course not found!", exception.getMessage());
    }

    @Test
    public void testAllStudentsAssignedToACourseNoStudents() {

        Mockito.when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));


        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            courseService.allStudentsAssignedToACourse(1);
        });
        assertEquals("Course has no students!", exception.getMessage());
    }
}
