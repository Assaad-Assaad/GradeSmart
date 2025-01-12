package be.ehb.gradesmart.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name cannot be blank!")
    private String name;

    @PositiveOrZero
    @Column(name = "age", nullable = false)
    @NotNull
    private int age;

    @Column(name = "active", nullable = false)
    private boolean active;


    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("students")
    private List<Course> courses = new ArrayList<>();

    public Student() {}

    public Student(@NotBlank String name, @NotNull int age, @NotNull boolean active) {
        this.name = name;
        this.age = age;
        this.active = active;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public int getAge() {return age;}

    public void setAge(int age) {this.age = age;}

    public boolean isActive() {return active;}

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
