package be.ehb.gradesmart;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import be.ehb.gradesmart.controller.StudentController;
import be.ehb.gradesmart.model.Student;
import be.ehb.gradesmart.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StudentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void testGetAllStudents() throws Exception {
        List<Student> students = Arrays.asList(new Student("John", 20, true), new Student("Jane", 21, true));

        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetStudentById() throws Exception {

        Student student = new Student("John", 20, true);
        student.setId(1);


        when(studentService.getStudentById(1)).thenReturn(student);


        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    public void testSaveStudent() throws Exception {

        Student student = new Student("John", 20, true);


        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"age\":20,\"active\":true}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCourses() throws Exception {
        Student student = new Student("John", 20, true);
        student.setId(1);

        when(studentService.allCoursesAssignedToAStudent(1))
                .thenReturn(Map.of("studentName", "John", "courses", Arrays.asList("Math", "Science")));

        mockMvc.perform(get("/students/1/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentName").value("John"))
                .andExpect(jsonPath("$.courses.length()").value(2));
    }

    @Test
    public void testAssignCourseToStudent() throws Exception {
        mockMvc.perform(post("/students/1/courses/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Course assigned to student successfully!"));
    }
}
