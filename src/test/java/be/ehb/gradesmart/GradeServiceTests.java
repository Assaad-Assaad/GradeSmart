package be.ehb.gradesmart;

import be.ehb.gradesmart.model.Course;
import be.ehb.gradesmart.model.Grade;
import be.ehb.gradesmart.model.Student;
import be.ehb.gradesmart.repository.GradeRepository;
import be.ehb.gradesmart.service.GradeService;
import be.ehb.gradesmart.service.exeption.ResourceAlreadyExistsException;
import be.ehb.gradesmart.service.exeption.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class GradeServiceTests {

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GradeService gradeService;

    private Student student;
    private Course course;
    private Grade grade;

    @BeforeEach
    public void setUp() {
        student = new Student("John Doe", 20, true);
        course = new Course("Mathematics", "Dr. Smith", 5);
        grade = new Grade(15.0, student, course);
    }

    @Test
    public void testAddGrade() {
        Mockito.when(gradeRepository.existsByStudentAndCourse(student, course)).thenReturn(false);
        Mockito.when(gradeRepository.save(any(Grade.class))).thenReturn(grade);

        Grade result = gradeService.addGrade(15.0, student, course);

        assertNotNull(result);
        assertEquals(15.0, result.getScore());
        assertEquals(student, result.getStudent());
        assertEquals(course, result.getCourse());

        Mockito.verify(gradeRepository, Mockito.times(1)).save(any(Grade.class));
    }

    @Test
    public void testAddGradeAlreadyExists() {
        Mockito.when(gradeRepository.existsByStudentAndCourse(student, course)).thenReturn(true);

        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
            gradeService.addGrade(15.0, student, course);
        });

        assertEquals("Student has already been graded for this course", exception.getMessage());
    }

    @Test
    public void testAverageGradesForStudent() {
        List<Grade> grades = Arrays.asList(
                new Grade(15.0, student, course),
                new Grade(18.0, student, course)
        );
        Mockito.when(gradeRepository.findAllByStudent(student)).thenReturn(grades);

        double average = gradeService.averageGradesForStudent(student);

        assertEquals(16.5, average);
    }

    @Test
    public void testAverageGradesForStudentNotFound() {
        Mockito.when(gradeRepository.findAllByStudent(student)).thenReturn(List.of());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            gradeService.averageGradesForStudent(student);
        });

        assertEquals("No grades found for this student", exception.getMessage());
    }
}
