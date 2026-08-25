FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .

ARG SERVICE_MODULE
RUN ./gradlew :${SERVICE_MODULE}:bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app

ARG SERVICE_MODULE
COPY --from=build /app/${SERVICE_MODULE}/build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
