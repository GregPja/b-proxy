FROM openjdk:11-jdk-slim

COPY ./build/libs/bouldering-proxy-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080:8080

ENTRYPOINT ["java","-jar","/app.jar"]


