FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

ARG GITHUB_USERNAME
ARG GITHUB_TOKEN

COPY settings.xml /root/.m2/settings.xml

RUN sed -i 's|${env.GITHUB_USERNAME}|'${GITHUB_USERNAME}'|g' /root/.m2/settings.xml && \
    sed -i 's|${env.GITHUB_TOKEN}|'${GITHUB_TOKEN}'|g' /root/.m2/settings.xml

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]