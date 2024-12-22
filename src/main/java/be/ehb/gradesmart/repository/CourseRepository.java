package be.ehb.gradesmart.repository;

import be.ehb.gradesmart.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CourseRepository extends JpaRepository<Course, Integer> { }
