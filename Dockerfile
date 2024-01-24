FROM gradle:7.4.2-jdk17-alpine AS build

WORKDIR /usr/src/app

COPY . .

RUN gradle --no-daemon bootWar -x check

FROM docker.io/openjdk:18.0.1-jdk-slim

WORKDIR /idde

COPY bfim2114-spring/src/main/resources /idde/WEB-INF/classes

COPY --from=build /usr/src/app/build/libs/*.war bfim2114-spring-1.0-SNAPSHOT.war

EXPOSE 8081

CMD ["java", "-jar", "bfim2114-spring-1.0-SNAPSHOT.war"]
