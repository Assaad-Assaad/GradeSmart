package be.ehb.gradesmart;

import be.ehb.gradesmart.model.Student;
import be.ehb.gradesmart.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StudentRepositoryTests {
    @Autowired
    private StudentRepository studentRepository;

    private Student activeStudent;
    private Student inactiveStudent;

    @BeforeEach
    public void setup() {

        activeStudent = new Student("Alice", 22, true);
        studentRepository.save(activeStudent);


        inactiveStudent = new Student("Bob", 24, false);
        studentRepository.save(inactiveStudent);
    }

    @Test
    public void testFindByActiveTrue() {

        List<Student> activeStudents = studentRepository.findAByActive(true);


        assertNotNull(activeStudents, "The list of active students should not be null.");
        assertEquals(1, activeStudents.size(), "There should be exactly one active student.");
        assertEquals("Alice", activeStudents.get(0).getName(), "The active student should be Alice.");
    }

    @Test
    public void testFindByActiveFalse() {

        List<Student> inactiveStudents = studentRepository.findAByActive(false);


        assertNotNull(inactiveStudents, "The list of inactive students should not be null.");
        assertEquals(1, inactiveStudents.size(), "There should be exactly one inactive student.");
        assertEquals("Bob", inactiveStudents.get(0).getName(), "The inactive student should be Bob.");
    }
}
