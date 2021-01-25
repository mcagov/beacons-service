# Build the service
FROM gradle:jdk11-hotspot AS build
WORKDIR /opt/app
COPY . ./
RUN ./gradlew clean assemble

# Run the service
FROM adoptopenjdk:15-jre-hotspot AS beacons-service
WORKDIR /opt/app
COPY --from=build /opt/app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
