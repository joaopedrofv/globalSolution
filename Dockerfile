FROM gradle:8-jdk21-alpine AS build

WORKDIR /app

COPY build.gradle settings.gradle ./

RUN gradle build -x test --no-daemon || return 0

COPY . .

RUN gradle build -x test --no-daemon --info

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]