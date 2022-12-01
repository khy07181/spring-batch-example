FROM gradle:7.4.1-jdk11 AS build
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle clean build --no-daemon

FROM openjdk:11
ARG JAR_FILE_PATH=build/libs/*SNAPSHOT.jar
COPY ${JAR_FILE_PATH} app.jar
ENV PROFILE="prod"
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "app.jar"]
