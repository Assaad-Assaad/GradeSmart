package be.ehb.gradesmart.repository;


import be.ehb.gradesmart.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

   List<Student> findAByActive(boolean active);
}
