FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

RUN mkdir -p /root/.m2
RUN echo "<settings> \
             <servers> \
               <server> \
                 <id>github</id> \
                 <username>${GITHUB_USERNAME}</username> \
                 <password>${GITHUB_TOKEN}</password> \
               </server> \
             </servers> \
             <activeProfiles> \
               <activeProfile>github</activeProfile> \
             </activeProfiles> \
          </settings>" > /root/.m2/settings.xml

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]