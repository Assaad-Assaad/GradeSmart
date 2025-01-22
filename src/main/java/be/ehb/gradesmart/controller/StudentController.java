package be.ehb.gradesmart.controller;


import be.ehb.gradesmart.model.Student;
import be.ehb.gradesmart.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/students")
public class StudentController {

   private final StudentService studentService;


   @Autowired
   public StudentController(StudentService studentService) {
       this.studentService = studentService;

   }

    @GetMapping
    List<Student> getAllStudents(@RequestParam(value = "active", required = false) Boolean active) {

        if (active != null && active){
            return studentService.getAllActiveStudents();

        }
        return studentService.getAllStudents();

   }


    @GetMapping("/{id}")
    ResponseEntity<Student> getStudentById(@PathVariable int id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }



@DeleteMapping("{id}")
void deleteStudentById(@PathVariable int id) {
      studentService.delete(id);
}

    @PostMapping
    void saveStudent(@Valid @RequestBody Student student){
        studentService.save(student);
    }

    @GetMapping("/{id}/courses")
    Map<String, Object> getCourses(@PathVariable int id) {

       return studentService.allCoursesAssignedToAStudent(id);
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<String> assignCourseToStudent(
            @PathVariable int studentId,
            @PathVariable int courseId) {
        studentService.assignStudentToCourse(courseId, studentId);
        return ResponseEntity.ok("Course assigned to student successfully!");
    }

}
