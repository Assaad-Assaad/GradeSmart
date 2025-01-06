package be.ehb.gradesmart;
import be.ehb.gradesmart.model.Course;
import be.ehb.gradesmart.model.Grade;
import be.ehb.gradesmart.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GradeTests {
    @Test
    void testConstructorAndGetters() {
        Student student = new Student();
        student.setId(1);

        Course course = new Course();
        course.setId(1);

        Grade grade = new Grade(15.5, student, course);

        assertEquals(15.5, grade.getScore());
        assertEquals(student, grade.getStudent());
        assertEquals(course, grade.getCourse());
    }

    @Test
    void testSetters() {
        Student student = new Student();
        student.setId(2);

        Course course = new Course();
        course.setId(2);

        Grade grade = new Grade();

        grade.setId(10);
        grade.setScore(18.0);
        grade.setStudent(student);
        grade.setCourse(course);

        assertEquals(10, grade.getId());
        assertEquals(18.0, grade.getScore());
        assertEquals(student, grade.getStudent());
        assertEquals(course, grade.getCourse());
    }

    @Test
    void testScoreValidation() {
        Grade grade = new Grade();

        grade.setScore(12.0);
        assertEquals(12.0, grade.getScore());

        Exception exceptionLow = assertThrows(IllegalArgumentException.class, () -> grade.setScore(-1.0));
        assertEquals("Score must be between 0 and 20.", exceptionLow.getMessage());

        Exception exceptionHigh = assertThrows(IllegalArgumentException.class, () -> grade.setScore(21.0));
        assertEquals("Score must be between 0 and 20.", exceptionHigh.getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        Grade grade1 = new Grade();
        grade1.setId(1);

        Grade grade2 = new Grade();
        grade2.setId(1);

        assertEquals(grade1, grade2);

        assertEquals(grade1.hashCode(), grade2.hashCode());

        grade2.setId(2);
        assertNotEquals(grade1, grade2);
        assertNotEquals(grade1.hashCode(), grade2.hashCode());
    }
}
