package be.ehb.gradesmart;

import be.ehb.gradesmart.model.Course;
import be.ehb.gradesmart.model.Grade;
import be.ehb.gradesmart.model.Student;
import be.ehb.gradesmart.repository.CourseRepository;
import be.ehb.gradesmart.repository.GradeRepository;
import be.ehb.gradesmart.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest

public class GradeRepositoryTests {
    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Student student;
    private Course course;

    @BeforeEach
    public void setup() {
        student = new Student("John Doe", 20, true);
        student = studentRepository.save(student);

        course = new Course("Prof. Smith", "Mathematics", 5);
        course = courseRepository.save(course);
    }

    @Test
    public void testExistsByStudentAndCourse() {

        Grade grade = new Grade(18.5, student, course);
        gradeRepository.save(grade);


        boolean exists = gradeRepository.existsByStudentAndCourse(student, course);


        assertTrue(exists, "The grade should exist for the given student and course.");
    }

    @Test
    public void testFindAllByStudent() {

        Grade grade1 = new Grade(18.5, student, course);
        Grade grade2 = new Grade(15.0, student, new Course("Dr. Brown", "Physics", 4));
        gradeRepository.save(grade1);
        gradeRepository.save(grade2);


        List<Grade> grades = gradeRepository.findAllByStudent(student);


        assertNotNull(grades, "Grades list should not be null.");
        assertEquals(2, grades.size(), "The student should have 2 grades.");
    }

    @Test
    public void testSaveGrade() {

        Grade grade = new Grade(19.0, student, course);

        Grade savedGrade = gradeRepository.save(grade);


        assertNotNull(savedGrade.getId(), "The saved grade should have a generated ID.");
        assertEquals(19.0, savedGrade.getScore(), "The score of the saved grade should match.");
    }
}
