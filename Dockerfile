FROM  adoptopenjdk/openjdk11:alpine AS base
WORKDIR /app
COPY . ./
RUN ./gradlew clean build

#TODO: Add unit-test step
#FROM something AS unit-test
#
#RUN ./gradlew clean check

FROM adoptopenjdk/openjdk11:alpine AS beacons-service
COPY --from=base /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
