package be.ehb.gradesmart.service;

import be.ehb.gradesmart.model.Course;
import be.ehb.gradesmart.model.Student;
import be.ehb.gradesmart.repository.CourseRepository;
import be.ehb.gradesmart.repository.StudentRepository;
import be.ehb.gradesmart.service.exeption.CreditsReachedException;
import be.ehb.gradesmart.service.exeption.ResourceAlreadyExistsException;
import be.ehb.gradesmart.service.exeption.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {

        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }

    public Student getStudentById(int id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " is not found!"));
    }

    public void save(Student student) {

        studentRepository.save(student);
    }


    public List<Student> getAllActiveStudents() {
        return studentRepository.findAByActive(true);
    }

    // Get all courses that assigned to a student and display only the names
    public Map<String, Object> allCoursesAssignedToAStudent(int studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + studentId + " is not found!"));

        if(student.getCourses().isEmpty()){
            throw new ResourceNotFoundException("There are no courses assigned to student with id " + studentId);
        }

        String studentName = student.getName();
        List<String> courses = student.getCourses().stream()
                .map(Course::getName)
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("studentName", studentName);
        result.put("courses", courses);
        return result;
    }



    public void assignStudentToCourse(int courseId, int studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found!"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found!"));

        if(student.getCourses().contains(course)) {
            throw new ResourceAlreadyExistsException("Student already assigned to course!");
        }
        int totalCredits = student.getCourses().stream()
                .mapToInt(Course::getCredits)
                .sum() + course.getCredits();

        if(totalCredits >= 60){
            throw new CreditsReachedException("Assigning this course exceeds the maximum allowed 60 credits!");
        }
        student.getCourses().add(course);
        studentRepository.save(student);

    }

}
