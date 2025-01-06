package be.ehb.gradesmart;
import be.ehb.gradesmart.model.Course;
import be.ehb.gradesmart.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest

public class CourseRepositoryTests {
    @Autowired
    private CourseRepository courseRepository;

    @Test
    void testSaveAndFindCourse() {
        Course course = new Course("Prof. John Doe", "Mathematics", 6);


        Course savedCourse = courseRepository.save(course);
        Optional<Course> retrievedCourse = courseRepository.findById(savedCourse.getId());


        assertTrue(retrievedCourse.isPresent());
        assertEquals("Prof. John Doe", retrievedCourse.get().getLector());
        assertEquals("Mathematics", retrievedCourse.get().getName());
        assertEquals(6, retrievedCourse.get().getCredits());
    }

    @Test
    void testFindAllCourses() {

        Course course1 = new Course("Prof. John Doe", "Mathematics", 6);
        Course course2 = new Course("Dr. Jane Smith", "Physics", 4);

        courseRepository.save(course1);
        courseRepository.save(course2);


        List<Course> courses = courseRepository.findAll();


        assertEquals(2, courses.size());
    }

    @Test
    void testDeleteCourse() {

        Course course = new Course("Prof. John Doe", "Mathematics", 6);
        Course savedCourse = courseRepository.save(course);


        courseRepository.deleteById(savedCourse.getId());
        Optional<Course> retrievedCourse = courseRepository.findById(savedCourse.getId());


        assertFalse(retrievedCourse.isPresent());
    }
}
