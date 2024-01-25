# Use Gradle image to build the Spring Boot application
FROM gradle:7.4.2-jdk18-alpine AS build

# Set the working directory to the Spring Boot project directory within the Docker image
WORKDIR /home/gradle/bfim2114-spring

WORKDIR /usr/src/app

# Copy the Gradle wrapper from the root project directory
COPY . .

# Use the Gradle wrapper to build the project
RUN gradle --no-daemon bootWar -x check

# Use OpenJDK image to run the Spring Boot application
FROM openjdk:18.0.1-jdk-slim

# Set the working directory in the Docker image
WORKDIR /idde

# Copy the built WAR file from the build stage
COPY --from=build /usr/src/app/build/libs/*.war app.war

# Expose the port the application runs on
EXPOSE 8081

# Run the application
CMD ["java", "-jar", "app.war"]
