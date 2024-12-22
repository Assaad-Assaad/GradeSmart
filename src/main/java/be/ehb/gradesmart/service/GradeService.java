package be.ehb.gradesmart.service;

import be.ehb.gradesmart.model.Course;
import be.ehb.gradesmart.model.Grade;
import be.ehb.gradesmart.model.Student;
import be.ehb.gradesmart.repository.GradeRepository;
import be.ehb.gradesmart.service.exeption.ResourceAlreadyExistsException;
import be.ehb.gradesmart.service.exeption.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {
    private final GradeRepository gradeRepository;



    @Autowired
    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;

    }

    public Grade addGrade(double score, Student student, Course course) {

        if(gradeRepository.existsByStudentAndCourse(student, course)) {
            throw new ResourceAlreadyExistsException("Student has already been graded for this course");
        }

        Grade grade = new Grade();
        grade.setCourse(course);
        grade.setStudent(student);
        grade.setScore(score);

        return gradeRepository.save(grade);
    }

    public double averageGradesForStudent(Student student) {
        List<Grade> grades = gradeRepository.findAllByStudent(student);
        if(grades.isEmpty()) {
            throw new ResourceNotFoundException("No grades found for this student");
        }

        return grades.stream()
                .mapToDouble(Grade::getScore)
                .average()
                .orElseThrow(() -> new IllegalStateException("Error calculating average"));

    }
}
