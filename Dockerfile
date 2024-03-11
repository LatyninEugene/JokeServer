FROM eclipse-temurin:17-jdk-jammy as build
WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM eclipse-temurin:17-jre-jammy
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
