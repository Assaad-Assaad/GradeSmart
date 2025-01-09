package be.ehb.gradesmart.service;

import be.ehb.gradesmart.model.Course;
import be.ehb.gradesmart.model.Grade;
import be.ehb.gradesmart.model.Student;
import be.ehb.gradesmart.repository.GradeRepository;
import be.ehb.gradesmart.service.exeption.ResourceAlreadyExistsException;
import be.ehb.gradesmart.service.exeption.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GradeService gradeService;

    private Student student;
    private Course course;

    @BeforeEach
    void setUp() {
        student = new Student("Tom", 25, true);
        student.setId(1);
        course = new Course("Sam", "Java", 5);
        course.setId(1);
    }

    @Test
    void shouldThrowResourceAlreadyExistsException_whenStudentAlreadyGradedForCourse() {
        when(gradeRepository.existsByStudentAndCourse(student, course)).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> {
            gradeService.addGrade(15, student, course);
        });
    }

    @Test
    void shouldAddGradeSuccessfully_whenNoExistingGradeForStudentAndCourse() {
        when(gradeRepository.existsByStudentAndCourse(student, course)).thenReturn(false);
        when(gradeRepository.save(any(Grade.class))).thenReturn(new Grade(15, student, course));

        Grade grade = gradeService.addGrade(15, student, course);

        assertNotNull(grade);
        assertEquals(15, grade.getScore());
        assertEquals(student, grade.getStudent());
        assertEquals(course, grade.getCourse());

        verify(gradeRepository).save(any(Grade.class));
    }


    @Test
    void shouldThrowResourceNotFoundException_whenNoGradesForStudent() {
        when(gradeRepository.findAllByStudent(student)).thenReturn(Arrays.asList());

        assertThrows(ResourceNotFoundException.class, () -> {
            gradeService.averageGradesForStudent(student);
        });
    }

    @Test
    void shouldReturnCorrectAverageGradeForStudent_whenGradesExist() {
        Grade grade1 = new Grade(13, student, course );
        Grade grade2 = new Grade(17, student, new Course("Leo", "Python", 5));
        List<Grade> grades = Arrays.asList(grade1, grade2);

        when(gradeRepository.findAllByStudent(student)).thenReturn(grades);

        double average = gradeService.averageGradesForStudent(student);

        assertEquals(15, average);
    }
}
