package be.ehb.gradesmart.repository;

import be.ehb.gradesmart.model.Course;
import be.ehb.gradesmart.model.Grade;
import be.ehb.gradesmart.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer> {

    boolean existsByStudentAndCourse(Student student, Course course);

    List<Grade> findAllByStudent(Student student);
}
