FROM eclipse-temurin:21.0.1_12-jdk-alpine AS build

WORKDIR /workspace/app

COPY gradlew settings.gradle.kts ./
COPY app app
COPY gradle gradle

RUN ./gradlew build -x test

FROM eclipse-temurin:21.0.1_12-jdk-alpine

COPY --from=build /workspace/app/app/build/libs ./

ENTRYPOINT ["java", "-jar", "./app-0.0.1.jar"]
