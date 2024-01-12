# Build Stage
FROM maven:3.9.6-eclipse-temurin-17-alpine as build

# Set the working directory inside the container
WORKDIR /app

# Copy the project's POM file to the working directory
COPY ./pom.xml ./pom.xml

# Copy the entire source code to the working directory
COPY ./src ./src

# Run Maven commands to build the application
RUN ["mvn", "clean", "package", "-DskipTests"]

FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the compiled JAR file from the build stage to the working directory
COPY ./target/AFTAS-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080

# Specify the command to run when the container starts
ENTRYPOINT ["java", "-jar", "AFTAS-0.0.1-SNAPSHOT.jar"]