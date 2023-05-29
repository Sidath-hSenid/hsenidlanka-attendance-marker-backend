
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app


COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine AS runtime

WORKDIR /app

COPY ./target/attendance-marker-backend-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "attendance-marker-backend-0.0.1-SNAPSHOT.jar"]