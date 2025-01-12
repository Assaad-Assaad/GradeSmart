FROM openjdk:21-jdk
WORKDIR /app


ARG JAR_FILE
COPY ${JAR_FILE} GradeSmart-0.0.1-SNAPSHOT.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/GradeSmart-0.0.1-SNAPSHOT.jar"]
