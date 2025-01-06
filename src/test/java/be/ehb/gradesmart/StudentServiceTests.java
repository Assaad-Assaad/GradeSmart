package be.ehb.gradesmart;
import be.ehb.gradesmart.model.Course;
import be.ehb.gradesmart.model.Student;
import be.ehb.gradesmart.repository.CourseRepository;
import be.ehb.gradesmart.repository.StudentRepository;
import be.ehb.gradesmart.service.StudentService;
import be.ehb.gradesmart.service.exeption.CreditsReachedException;
import be.ehb.gradesmart.service.exeption.ResourceAlreadyExistsException;
import be.ehb.gradesmart.service.exeption.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest

public class StudentServiceTests {
    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;
    private Course course;

    @BeforeEach
    public void setUp() {

        student = new Student("John Doe", 20, true);
        course = new Course("Mathematics", "Dr. Smith", 5);
    }

    @Test
    public void testGetAllStudents() {

        List<Student> students = Arrays.asList(student);
        Mockito.when(studentRepository.findAll()).thenReturn(students);


        List<Student> result = studentService.getAllStudents();


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    public void testGetStudentById() {

        Mockito.when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));


        Student result = studentService.getStudentById(1);


        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void testGetStudentByIdNotFound() {

        Mockito.when(studentRepository.findById(anyInt())).thenReturn(Optional.empty());


        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            studentService.getStudentById(1);
        });
        assertEquals("Student with id 1 is not found!", exception.getMessage());
    }

    @Test
    public void testAssignStudentToCourse() {

        Mockito.when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        Mockito.when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));


        studentService.assignStudentToCourse(1, 1);


        assertTrue(student.getCourses().contains(course));
    }

    @Test
    public void testAssignStudentToCourseAlreadyAssigned() {

        student.getCourses().add(course);
        Mockito.when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        Mockito.when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));


        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
            studentService.assignStudentToCourse(1, 1);
        });
        assertEquals("Student already assigned to course!", exception.getMessage());
    }

    @Test
    public void testAssignStudentToCourseExceedCredits() {
        // Arranger
        student.getCourses().add(course);
        Course anotherCourse = new Course("Science", "Dr. Johnson", 55);
        student.getCourses().add(anotherCourse);
        Mockito.when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        Mockito.when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));


        CreditsReachedException exception = assertThrows(CreditsReachedException.class, () -> {
            studentService.assignStudentToCourse(2, 1);
        });
        assertEquals("Assigning this course exceeds the maximum allowed 60 credits!", exception.getMessage());
    }

    @Test
    public void testAllCoursesAssignedToAStudent() {

        Mockito.when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        student.getCourses().add(course);


        Map<String, Object> result = studentService.allCoursesAssignedToAStudent(1);


        assertNotNull(result);
        assertEquals("John Doe", result.get("studentName"));
        assertTrue(((List<?>) result.get("courses")).contains("Mathematics"));
    }

    @Test
    public void testAllCoursesAssignedToAStudentNotFound() {
        Mockito.when(studentRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            studentService.allCoursesAssignedToAStudent(1);
        });
        assertEquals("Student with id 1 is not found!", exception.getMessage());
    }
}
