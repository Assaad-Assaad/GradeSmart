package be.ehb.gradesmart;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import be.ehb.gradesmart.controller.CoursesController;
import be.ehb.gradesmart.model.Course;
import be.ehb.gradesmart.service.CourseService;
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
import java.util.Map;

public class CoursesControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CoursesController coursesController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(coursesController).build();
    }

    @Test
    public void testSaveCourse() throws Exception {
        Course course = new Course("Dr. Smith", "Mathematics", 5);

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Mathematics\",\"credits\":5}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStudentsByCourse() throws Exception {

        Map<String, Object> studentsInCourse = Map.of(
                "courseName", "Mathematics",
                "studentNames", Arrays.asList("John", "Jane")
        );

        when(courseService.allStudentsAssignedToACourse(1)).thenReturn(studentsInCourse);

        mockMvc.perform(get("/courses/1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Mathematics"))
                .andExpect(jsonPath("$.studentNames.length()").value(2));
    }
}
