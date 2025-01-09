FROM openjdk:21-jdk
WORKDIR /app
COPY target/GradeSmart-0.0.1-SNAPSHOT.jar GradeSmart-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/GradeSmart-0.0.1-SNAPSHOT.jar"]