# Grade Smart Application

## Overview
**GradeSmart** is a RESTful API built with **Spring Boot** and **Maven**. It is designed for managing students, courses, and their relationships while maintaining a credit limit for students. The project integrates a fully automated **CI/CD pipeline** using **GitHub Actions** to ensure consistent quality and deployment.

---

## Project Objectives
This project aims to:
- Manage students, courses, and their relationships effectively.
- Enforce credit constraints for students.
- Demonstrate a complete CI/CD pipeline for a Java-based API.
- Provide containerized deployment using **Docker**.

---

## Features
1. **Student Management**:
   - Add, edit, and delete student records.
2. **Course Management**:
   - Add, edit, and delete course records.
3. **Student-Course Relationships**:
   - Assign students to courses and courses to students.
   - Enforce credit limits for students.
4. **Credit Management**:
   - Query the total credits assigned to a student.
   - Prevent students from exceeding the maximum allowable credits.

---

## API Endpoints

### Students
| HTTP Method | Endpoint         | Description             |
|-------------|------------------|-------------------------|
| GET         | `/students`      | Retrieve all students.  |
| POST        | `/students`      | Add a new student.      |
| PUT         | `/students/{id}` | Update student details. |
| DELETE      | `/students/{id}` | Delete a student.       |

### Courses
| HTTP Method | Endpoint         | Description            |
|-------------|------------------|------------------------|
| GET         | `/courses`       | Retrieve all courses.  |
| POST        | `/courses`       | Add a new course.      |
| PUT         | `/courses/{id}`  | Update course details. |
| DELETE      | `/courses/{id}`  | Delete a course.       |

### Student-Course Relationships
| HTTP Method | Endpoint                  | Description                                   |
|-------------|---------------------------|-----------------------------------------------|
| POST        | `/students/{id}/courses`  | Assign a course to a student.                |
| GET         | `/students/{id}/credits`  | Retrieve the total credits of a student.     |
| DELETE      | `/students/{id}/courses/{courseId}` | Remove a course from a student. |

---

## CI/CD Pipeline
The application incorporates a robust **CI/CD pipeline** using **GitHub Actions** to ensure code quality and streamline deployments.

### Pipeline Workflow
- **Triggers**:
  - Runs automatically on `push` and `pull request` events.
- **Steps**:
  1. **Checkout Code**: Pulls the latest code from the repository.
  2. **Build and Test**:
     - Uses Maven to compile the project.
     - Runs unit and integration tests to ensure code correctness.
  3. **Docker Build**:
     - Builds a Docker image of the application.
  4. **Deployment**:
     - Deploys the Docker image to Docker Hub.

### Docker Repository
The Docker image for the Grade Smart application is available on Docker Hub: [GradeSmart Docker Repository](https://hub.docker.com)

---

## Credit Management Logic
1. Each student is assigned a maximum allowable credit limit.
2. When a course is assigned to a student, the application checks if adding the course’s credits will exceed the limit.
   - If yes, the operation is rejected.
   - If no, the assignment proceeds.
3. Credits can be queried through the `/students/{id}/credits` endpoint.

---

## Deployment
The application is containerized using **Docker**, allowing it to be easily deployed across environments.

### Deployment Steps
1. Ensure Docker is installed and running.
2.	Pull the latest Docker image:
docker pull asaad90/gradesmart:latest
3.	Run the application:
docker run -p 8080:8080 asaad90/gradesmart:latest
4.	Access the API at http://localhost:8080.

---
## Conclusion
The Grade Smart application provides a seamless way to manage students and courses while enforcing credit constraints. With automated CI/CD integration, the application ensures continuous delivery and deployment, maintaining high standards of code quality and operational efficiency.



