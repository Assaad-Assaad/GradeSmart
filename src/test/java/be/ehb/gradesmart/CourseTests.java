package be.ehb.gradesmart;
import be.ehb.gradesmart.model.Course;
import be.ehb.gradesmart.model.Student;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;


public class CourseTests {
    @Test
    void testConstructorAndGetters() {
        Course course = new Course("Dr. Smith", "Mathematics", 5);

        assertEquals("Dr. Smith", course.getLector());
        assertEquals("Mathematics", course.getName());
        assertEquals(5, course.getCredits());
        assertNotNull(course.getStudents());
        assertTrue(course.getStudents().isEmpty());
    }

    @Test
    void testSetters() {
        Course course = new Course();

        course.setId(1);
        course.setLector("Dr. Brown");
        course.setName("Physics");
        course.setCredits(4);

        assertEquals(1, course.getId());
        assertEquals("Dr. Brown", course.getLector());
        assertEquals("Physics", course.getName());
        assertEquals(4, course.getCredits());
    }

    @Test
    void testStudentsList() {
        Course course = new Course();

        Student student1 = new Student();
        Student student2 = new Student();

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        course.setStudents(students);

        assertEquals(2, course.getStudents().size());
        assertTrue(course.getStudents().contains(student1));
        assertTrue(course.getStudents().contains(student2));
    }

    @Test
    void testEqualsAndHashCode() {
        Course course1 = new Course("Dr. Green", "Chemistry", 3);
        course1.setId(1);

        Course course2 = new Course("Dr. White", "Biology", 4);
        course2.setId(1);

        assertEquals(course1, course2);

        assertEquals(course1.hashCode(), course2.hashCode());

        course2.setId(2);
        assertNotEquals(course1, course2);
        assertNotEquals(course1.hashCode(), course2.hashCode());
    }
}
