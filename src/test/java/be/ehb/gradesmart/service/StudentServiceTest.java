package be.ehb.gradesmart.service;

import be.ehb.gradesmart.model.Course;
import be.ehb.gradesmart.model.Student;
import be.ehb.gradesmart.repository.CourseRepository;
import be.ehb.gradesmart.repository.StudentRepository;
import be.ehb.gradesmart.service.exeption.CreditsReachedException;
import be.ehb.gradesmart.service.exeption.ResourceAlreadyExistsException;
import be.ehb.gradesmart.service.exeption.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepo;
    @Mock
    private CourseRepository courseRepo;
    @InjectMocks
    private StudentService studentService;

    private Student existingStudent;
    private Course existingCourse;

    @BeforeEach
    void setUp() {
        existingStudent = new Student("Tom", 25, true);
        existingStudent.setId(1);
        existingCourse = new Course("Sam", "Java", 55);
        existingCourse.setId(1);
    }

    @Test
    void shouldThrowCreditsReachedException_whenCreditsExceed60() {
        Course newCourse = new Course("Leo", "Python", 6);

        when(studentRepo.findById(1)).thenReturn(Optional.of(existingStudent));
        when(courseRepo.findById(1)).thenReturn(Optional.of(existingCourse));

        existingStudent.getCourses().add(newCourse);

        assertThrows(CreditsReachedException.class, () ->
                studentService.assignStudentToCourse(1, 1));
    }

    @Test
    void shouldThrowResourceNotFoundException_whenStudentIdDoesNotExist() {
        int invalidStudentId = 10;
        int courseId = 1;

        when(courseRepo.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(studentRepo.findById(invalidStudentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                studentService.assignStudentToCourse(courseId, invalidStudentId));
    }

    @Test
    void shouldThrowResourceNotFoundException_whenCourseIdDoesNotExist() {
        int studentId = 1;
        int invalidCourseId = 10;

        when(courseRepo.findById(invalidCourseId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                studentService.assignStudentToCourse(invalidCourseId, studentId));
    }

    @Test
    void shouldThrowResourceAlreadyExistsException_whenCourseAlreadyAssigned() {
        when(studentRepo.findById(1)).thenReturn(Optional.of(existingStudent));
        when(courseRepo.findById(1)).thenReturn(Optional.of(existingCourse));

        existingStudent.getCourses().add(existingCourse);

        assertThrows(ResourceAlreadyExistsException.class, () ->
                studentService.assignStudentToCourse(1, 1));
    }
}
