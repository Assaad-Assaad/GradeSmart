package be.ehb.gradesmart;

import be.ehb.gradesmart.model.Course;
import be.ehb.gradesmart.model.Student;

import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;

public class StudentTests {
    private final Validator validator;

    public StudentTests() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testConstructorAndGetters() {
        Student student = new Student("John Doe", 20, true);

        assertEquals("John Doe", student.getName());
        assertEquals(20, student.getAge());
        assertTrue(student.isActive());
        assertNotNull(student.getCourses());
        assertEquals(0, student.getCourses().size());
    }

    @Test
    void testSetters() {
        Student student = new Student();

        student.setId(1);
        student.setName("Jane Smith");
        student.setAge(25);
        student.setActive(false);

        assertEquals(1, student.getId());
        assertEquals("Jane Smith", student.getName());
        assertEquals(25, student.getAge());
        assertFalse(student.isActive());
    }

    @Test
    void testCoursesAssociation() {
        Student student = new Student();
        Course course1 = new Course("Dr. Smith", "Mathematics", 5);
        Course course2 = new Course("Prof. Brown", "Physics", 4);

        student.setCourses(List.of(course1, course2));

        assertEquals(2, student.getCourses().size());
        assertTrue(student.getCourses().contains(course1));
        assertTrue(student.getCourses().contains(course2));
    }

    @Test
    void testValidationConstraints() {
        Student student = new Student("", -1, true);

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Name cannot be blank!")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must be greater than or equal to 0")));
    }

    @Test
    void testEqualsAndHashCode() {
        Student student1 = new Student();
        student1.setId(1);

        Student student2 = new Student();
        student2.setId(1);

        assertEquals(student1, student2);

        assertEquals(student1.hashCode(), student2.hashCode());

        student2.setId(2);
        assertNotEquals(student1, student2);
        assertNotEquals(student1.hashCode(), student2.hashCode());
    }
}
