package be.ehb.gradesmart.service;

import be.ehb.gradesmart.model.Course;
import be.ehb.gradesmart.model.Student;
import be.ehb.gradesmart.repository.CourseRepository;
import be.ehb.gradesmart.service.exeption.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;


    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;

    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }


    public void save(Course course) {
        courseRepository.save(course);
    }

    //Get all students that assigned to a course and display only the names.
    public Map<String, Object> allStudentsAssignedToACourse(int courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found!"));

        if (course.getStudents().isEmpty()) {
            throw new ResourceNotFoundException("Course has no students!");
        }

        String courseName = course.getName();
        List<String> studentNames = course.getStudents().stream()
                .map(Student::getName)
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("courseName", courseName);
        result.put("studentNames", studentNames);

        return result;
    }

}
