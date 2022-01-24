FROM openjdk:15 AS builder
WORKDIR /app

COPY target/school-registration-0.0.1-SNAPSHOT.jar school-registration.jar

ENTRYPOINT [ "java","-jar","school-registration.jar" ]
