FROM gradle:7.4.2-jdk17-alpine AS build

WORKDIR /usr/src/app

COPY . .

RUN gradle --no-daemon bootWar -x check

FROM docker.io/openjdk:17.0.1-jdk-slim

WORKDIR /idde

COPY src/main/resources /sportgh/WEB-INF/classes

COPY --from=build /usr/src/app/build/libs/*.war app.war

EXPOSE 8081

CMD ["java", "-jar", "app.war"]
