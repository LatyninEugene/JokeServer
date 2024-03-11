FROM openjdk:17-jdk-slim as build
WORKDIR /app
COPY . /app
RUN ./gradlew bootJar

FROM openjdk:17-alpine
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
