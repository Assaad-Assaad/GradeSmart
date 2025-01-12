#FROM openjdk:21-jdk
#WORKDIR /app
#COPY target/GradeSmart-0.0.1-SNAPSHOT.jar GradeSmart-0.0.1-SNAPSHOT.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "/GradeSmart-0.0.1-SNAPSHOT.jar"]

FROM openjdk:21-jdk
WORKDIR /app

# Accept the JAR file as a build argument
ARG JAR_FILE
COPY ${JAR_FILE} GradeSmart-0.0.1-SNAPSHOT.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/GradeSmart-0.0.1-SNAPSHOT.jar"]
