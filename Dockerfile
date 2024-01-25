# Use Gradle image to build the Spring Boot application
FROM gradle:7.4.2-jdk18-alpine AS build

WORKDIR /usr/src/app

COPY . .

RUN gradle --no-daemon bootWar -x check

FROM openjdk:18.0.1-jdk-slim

WORKDIR /idde

COPY --from=build /usr/src/app/build/libs/bfim2114.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jarr"]
