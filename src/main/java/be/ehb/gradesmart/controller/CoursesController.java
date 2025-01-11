package be.ehb.gradesmart.controller;


import be.ehb.gradesmart.model.Course;
import be.ehb.gradesmart.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@Validated
@RestController
@RequestMapping("/courses")
public class CoursesController {

    private final CourseService courseService;

    @Autowired
    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    void saveCourse(@Valid @RequestBody Course course) {
        courseService.save(course);
    }

    @GetMapping("/{id}/students")
     Map<String, Object> getStudents(@PathVariable int id) {
        return courseService.allStudentsAssignedToACourse(id);
    }





}
