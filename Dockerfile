# Use Gradle image to build the Spring Boot application
FROM gradle:7.4.2-jdk18-alpine AS build

WORKDIR /usr/src/app

COPY . .

RUN gradle --no-daemon bootWar -x check

FROM openjdk:18.0.1-jdk-slim

WORKDIR /idde

COPY --from=build /usr/src/app/build/libs/*.war app.war

EXPOSE 8081

CMD ["java", "-jar", "app.war"]
