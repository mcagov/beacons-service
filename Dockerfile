#TODO: Multistage build, add .dockerignore with build dir
#FROM alpine:latest AS base
#
## Do building of artifact ./gradlew clean assemble or build (check that
#
#
#FROM something AS unit-test
#
#RUN ./gradlew clean check
#
#
#FROM base AS beacons-service

## or /opt/app check not sure
#WORKDIR /usr/app
#
#COPY . ./
#
#RUN rm -fr ./

FROM adoptopenjdk/openjdk11
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
