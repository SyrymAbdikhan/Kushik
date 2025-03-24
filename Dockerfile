FROM maven:3.9.9-amazoncorretto-21 AS build

WORKDIR /build

COPY pom.xml .
COPY .env .
COPY src ./src

RUN mvn clean package

FROM openjdk:25-bookworm

WORKDIR /app

COPY --from=build /build/target/kushik-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "kushik-0.0.1-SNAPSHOT.jar"]