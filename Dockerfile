FROM  adoptopenjdk/openjdk11:alpine AS build
WORKDIR /app
COPY . ./
RUN ./gradlew clean build

FROM adoptopenjdk/openjdk11:alpine AS unit-test
WORKDIR /app
COPY --from=build /app/src .
RUN ./gradlew clean check

FROM adoptopenjdk/openjdk11:alpine AS beacons-service
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
