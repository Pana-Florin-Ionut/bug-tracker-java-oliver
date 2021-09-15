FROM maven:3.6.0-jdk-11-slim AS build
COPY . /home/bugtracker
RUN mvn -f /home/bugtracker/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /home/bugtracker/target/bugtracker-1.0.1.jar /usr/local/lib/bugtracker.jar
EXPOSE 8080
EXPOSE 3306
ENTRYPOINT ["java","-jar","/usr/local/lib/bugtracker.jar"]